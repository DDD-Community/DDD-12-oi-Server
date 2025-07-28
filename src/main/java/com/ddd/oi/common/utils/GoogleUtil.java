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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
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
    public GoogleDTO.GoogleProfile requestProfileByAccessToken(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://www.googleapis.com/userinfo/v2/me",
                HttpMethod.GET,
                request,
                String.class
        );

        try {
            return new ObjectMapper().readValue(response.getBody(), GoogleDTO.GoogleProfile.class);
        } catch (JsonProcessingException e) {
            log.error("구글 사용자 정보 파싱 실패", e);
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
        redisUtil.deleteData("RT:" + userEmail);
    }
    public void logoutGoogle(String oauthAccessToken, String userEmail) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", oauthAccessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            restTemplate.postForEntity("https://oauth2.googleapis.com/revoke", request, String.class);

            redisUtil.deleteData("RT:" + userEmail);
        } catch (HttpClientErrorException e) {
            log.error("구글 로그아웃 실패: {}", e.getResponseBodyAsString());
            throw new OiException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
