package com.ddd.oi.common.utils;

import com.ddd.oi.auth.dto.profile.GoogleDTO;
import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import java.util.Collections;
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
    public GoogleDTO.GoogleProfile requestProfileByAccessToken(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    GsonFactory.getDefaultInstance()
            )
                    .setAudience(Collections.singletonList(clientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken != null) {
                Payload payload = idToken.getPayload();

                return GoogleDTO.GoogleProfile.builder()
                        .id(payload.getSubject())
                        .email(payload.getEmail())
                        .name((String) payload.get("name"))
//                        .picture((String) payload.get("picture"))
                        .build();
            } else {
                log.error("구글 ID 토큰 검증 실패");
                throw new OiException(ErrorCode.TOKEN_INVALID);
            }
        } catch (Exception e) {
            log.error("구글 ID 토큰 검증 중 예외 발생", e);
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
