package com.ddd.oi.common.utils;

import com.ddd.oi.auth.dto.GoogleDTO;
import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@RequiredArgsConstructor
public class GoogleUtil {
    private final RedisUtil redisUtil;
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.client-secret}")
    private String clientSecret;

    @Value("${google.redirect-uri}")
    private String redirectUri;
    public GoogleDTO.OAuthToken requestToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("code", code);
        params.add("redirect_uri", redirectUri);
        params.add("state", UUID.randomUUID().toString()); // csrf 방지 위해서 넣어놓음

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://oauth2.googleapis.com/token",
                HttpMethod.POST,
                request,
                String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(response.getBody(), GoogleDTO.OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new OiException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public GoogleDTO.GoogleProfile requestProfile(GoogleDTO.OAuthToken token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token.getAccess_token());

        HttpEntity<Void> profileRequest = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://www.googleapis.com/userinfo/v2/me",
                HttpMethod.GET,
                profileRequest,
                String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(response.getBody(), GoogleDTO.GoogleProfile.class);
        } catch (JsonProcessingException e) {
            throw new OiException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
    public void unlink(String providerId) {

        String refreshToken = redisUtil.getData("RT:" + providerId);

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new OiException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }

        String accessToken = reissueAccessToken(refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", accessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        restTemplate.exchange(
                "https://oauth2.googleapis.com/revoke",
                HttpMethod.POST,
                request,
                String.class
        );

        redisUtil.deleteData("RT:" + providerId);
        redisUtil.deleteData("AT:" + providerId);
    }

    private String reissueAccessToken(String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("refresh_token", refreshToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://oauth2.googleapis.com/token",
                HttpMethod.POST,
                request,
                String.class
        );

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(response.getBody()).get("access_token").asText();
        } catch (Exception e) {
            throw new OiException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
    public void unlink(String oauthAccessToken, String userEmail) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", oauthAccessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        restTemplate.exchange(
                "https://oauth2.googleapis.com/revoke",
                HttpMethod.POST,
                request,
                String.class
        );

        log.info(" 구글 연결 해제 완료: {}", userEmail);

        redisUtil.deleteData("RT:" + userEmail);
    }


}
