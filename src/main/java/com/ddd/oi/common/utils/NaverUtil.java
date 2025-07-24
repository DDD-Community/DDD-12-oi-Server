package com.ddd.oi.common.utils;

import com.ddd.oi.auth.dto.NaverDTO;
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
@RequiredArgsConstructor
@Slf4j
public class NaverUtil {
    private final RedisUtil redisUtil;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    @Value("${naver.redirect-uri}")
    private String redirectUri;

    public NaverDTO.NaverProfile requestProfileByAccessToken(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.GET,
                request,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(response.getBody(), NaverDTO.NaverProfile.class);
        } catch (JsonProcessingException e) {
            log.error("네이버 프로필 파싱 실패", e);
            throw new OiException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public void unlink(String oauthAccessToken, String userEmail) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "delete");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("access_token", oauthAccessToken);
        params.add("service_provider", "NAVER");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        restTemplate.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                request,
                String.class
        );

        redisUtil.deleteData("RT:" + userEmail);
        redisUtil.deleteData("AT:" + userEmail);

    }
    public void logoutNaver(String userEmail) {
        redisUtil.deleteData("RT:" + userEmail);
    }


}

