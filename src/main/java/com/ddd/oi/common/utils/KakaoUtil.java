package com.ddd.oi.common.utils;

import com.ddd.oi.auth.dto.KakaoDTO;
import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
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
    public KakaoDTO.OAuthToken requestToken(String accessCode) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", client);
        params.add("redirect_url", redirect);
        params.add("code", accessCode);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        KakaoDTO.OAuthToken oAuthToken = null;

        try {
            oAuthToken = objectMapper.readValue(response.getBody(), KakaoDTO.OAuthToken.class);
            log.info("oAuthToken : " + oAuthToken.getAccess_token());
        } catch (JsonProcessingException e) {
            throw new OiException(ErrorCode.INTERNAL_SERVER_ERROR); //TODO 에러코드 추후 수정
        }
        return oAuthToken;
    }

    public KakaoDTO.KakaoProfile requestProfile(KakaoDTO.OAuthToken oAuthToken){
        RestTemplate restTemplate2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();

        headers2.add("Authorization","Bearer "+ oAuthToken.getAccess_token());

        HttpEntity<Void> kakaoProfileRequest = new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = restTemplate2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                kakaoProfileRequest,
                String.class);


        ObjectMapper objectMapper = new ObjectMapper();

        KakaoDTO.KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper.readValue(response2.getBody(), KakaoDTO.KakaoProfile.class);
        } catch (JsonProcessingException e) {
            log.info(Arrays.toString(e.getStackTrace()));
            log.error("JSON 파싱 중 오류 발생: {}", Arrays.toString(e.getStackTrace()));
            throw new OiException(ErrorCode.INTERNAL_SERVER_ERROR); //TODO 에러코드 추후 수정
        }

        return kakaoProfile;
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
