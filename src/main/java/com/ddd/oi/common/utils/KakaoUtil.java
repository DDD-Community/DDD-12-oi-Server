package com.ddd.oi.common.utils;

import com.ddd.oi.auth.dto.profile.KakaoDTO;
import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@RequiredArgsConstructor
public class KakaoUtil {
    private final RedisUtil redisUtil;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Value("${kakao.client-id}")
    private String client;
    @Value("${kakao.admin-key}")
    private String adminKey;

    @Value("${kakao.redirect-uri}")
    private String redirect;
    public KakaoDTO.KakaoProfile requestProfileByAccessToken(String oauthAccessToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(oauthAccessToken);

            HttpEntity<Void> request = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.GET,
                    request,
                    String.class
            );

            JsonNode root = objectMapper.readTree(response.getBody());
            return KakaoDTO.KakaoProfile.fromJson(root);
        } catch (HttpClientErrorException e) {
            log.error("카카오 API 호출 실패: {}", e.getResponseBodyAsString());
            throw new OiException(ErrorCode.TOKEN_INVALID);
        } catch (Exception e) {
            log.error("카카오 사용자 정보 파싱 실패", e);
            throw new OiException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }



    public String getKakaoUserId(String oauthAccessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(oauthAccessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://kapi.kakao.com/v1/user/access_token_info",
                HttpMethod.GET,
                request,
                String.class
        );

        try {
            return objectMapper.readTree(response.getBody()).get("id").asText();
        } catch (Exception e) {
            throw new OiException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public void unlink(String kakaoUserId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + adminKey);
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("target_id_type", "user_id");
        body.add("target_id", kakaoUserId);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        restTemplate.exchange(
                "https://kapi.kakao.com/v1/user/unlink",
                HttpMethod.POST,
                request,
                String.class
        );

        redisUtil.deleteData("RT:" + kakaoUserId);
        redisUtil.deleteData("AT:" + kakaoUserId);

    }
    public void logoutKakao(String oauthAccessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + oauthAccessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            restTemplate.exchange(
                    "https://kapi.kakao.com/v1/user/logout",
                    HttpMethod.POST,
                    request,
                    String.class
            );
        } catch (HttpClientErrorException e) {
            log.error("카카오 로그아웃 실패: {}", e.getResponseBodyAsString());
            throw new OiException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


}
