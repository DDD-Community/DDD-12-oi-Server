package com.ddd.oi.auth.service;

import com.ddd.oi.auth.dto.AuthResponseDTO;
import com.ddd.oi.auth.dto.KakaoDTO;
import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.common.utils.CookieUtil;
import com.ddd.oi.common.utils.JWTUtil;
import com.ddd.oi.common.utils.KakaoUtil;
import com.ddd.oi.common.utils.RedisUtil;
import com.ddd.oi.user.domain.ProviderInfo;
import com.ddd.oi.user.domain.User;
import com.ddd.oi.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoUtil kakaoUtil;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final RedisUtil redisUtil;

    private final Long ACCESS_TOKEN_VALIDITY = 1000L * 60 * 60;
    private final Long REFRESH_TOKEN_VALIDITY = 1000L * 60 * 60 * 24 * 14;

    public AuthResponseDTO oAuthLogin(String accessCode, HttpServletResponse response) {

        KakaoDTO.OAuthToken oAuthToken = kakaoUtil.requestToken(accessCode);

        KakaoDTO.KakaoProfile kakaoProfile = kakaoUtil.requestProfile(oAuthToken);
        String email = kakaoProfile.getKakao_account().getEmail();

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> createNewUser(kakaoProfile));

        String accessToken = jwtUtil.createJwt(
                null,
                user.getEmail(),
                user.getRole().toString(),
                ACCESS_TOKEN_VALIDITY
        );

        String refreshToken = jwtUtil.createJwt(
                null,
                user.getEmail(),
                user.getRole().toString(),
                REFRESH_TOKEN_VALIDITY
        );

        redisUtil.setDataExpire(
                "RT:" + user.getEmail(),
                refreshToken,
                REFRESH_TOKEN_VALIDITY
        );
        int maxAgeSeconds = (int) (ACCESS_TOKEN_VALIDITY / 1000);
        CookieUtil.addCookie(response, "accessToken", accessToken, maxAgeSeconds);

        return new AuthResponseDTO(user, accessToken, refreshToken);
    }

    private User createNewUser(KakaoDTO.KakaoProfile profile) {
        return userRepository.save(
                AuthConverter.toUser(
                        profile.getKakao_account().getEmail(),
                        profile.getKakao_account().getProfile().getNickname(),
                        profile.getKakao_account().getProfile().getProfileImageUrl(),
                        ProviderInfo.KAKAO //TODO 카카오 이외에 다른 소셜로그인 시 변경
                )
        );
    }
    public AuthResponseDTO reissueAccessToken(String oldRefreshToken, HttpServletResponse response) {
        String email = jwtUtil.getEmail(oldRefreshToken);

        String redisKey = "RT:" + email;
        String storedToken = redisUtil.getData(redisKey);

        if (!storedToken.equals(oldRefreshToken)) {
            throw new OiException(ErrorCode.TOKEN_INVALID);
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));

        String newAccessToken = jwtUtil.createJwt(
                null,
                user.getEmail(),
                user.getRole().toString(),
                ACCESS_TOKEN_VALIDITY
        );

        String newRefreshToken = jwtUtil.createJwt(
                null,
                user.getEmail(),
                user.getRole().toString(),
                REFRESH_TOKEN_VALIDITY
        );

        redisUtil.deleteData(redisKey);
        redisUtil.setDataExpire(redisKey, newRefreshToken, REFRESH_TOKEN_VALIDITY);

        int maxAgeSeconds = (int) (ACCESS_TOKEN_VALIDITY / 1000);
        CookieUtil.addCookie(response, "accessToken", newAccessToken, maxAgeSeconds);

        return new AuthResponseDTO(user, newAccessToken, newRefreshToken);
    }


}