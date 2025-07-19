package com.ddd.oi.auth.service;

import com.ddd.oi.auth.dto.AuthResponseDTO;
import com.ddd.oi.auth.dto.GoogleProfileAdapter;
import com.ddd.oi.auth.dto.KakaoProfileAdapter;
import com.ddd.oi.auth.dto.NaverProfileAdapter;
import com.ddd.oi.auth.dto.OAuthProfile;
import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.common.utils.GoogleUtil;
import com.ddd.oi.common.utils.JWTUtil;
import com.ddd.oi.common.utils.KakaoUtil;
import com.ddd.oi.common.utils.NaverUtil;
import com.ddd.oi.common.utils.RedisUtil;
import com.ddd.oi.user.domain.ProviderInfo;
import com.ddd.oi.user.domain.RoleType;
import com.ddd.oi.user.domain.User;
import com.ddd.oi.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final KakaoUtil kakaoUtil;
    private final NaverUtil naverUtil;
    private final GoogleUtil googleUtil;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final RedisUtil redisUtil;

    private final Long ACCESS_TOKEN_VALIDITY = 1000L * 60 * 60;
    private final Long REFRESH_TOKEN_VALIDITY = 1000L * 60 * 60 * 24 * 14;

    public AuthResponseDTO oAuthLogin(ProviderInfo provider, String accessCode, HttpServletResponse response) {
        OAuthProfile profile;

        switch (provider) {
            case KAKAO -> {
                var oAuthToken = kakaoUtil.requestToken(accessCode);
                var kakaoProfile = kakaoUtil.requestProfile(oAuthToken);
                profile = new KakaoProfileAdapter(kakaoProfile);
            }
            case NAVER -> {
                var oAuthToken = naverUtil.requestToken(accessCode);
                var naverProfile = naverUtil.requestProfile(oAuthToken);
                profile = new NaverProfileAdapter(naverProfile);
            }
            case GOOGLE -> {
                var oAuthToken = googleUtil.requestToken(accessCode);
                var googleProfile = googleUtil.requestProfile(oAuthToken);
                profile = new GoogleProfileAdapter(googleProfile);
            }
            default -> throw new OiException(ErrorCode.BAD_REQUEST);
        }

        User user = userRepository.findByEmail(profile.getEmail())
                .map(existingUser -> {
                    existingUser.updateNickname(profile.getNickname());
                    existingUser.updateProfileUrl(profile.getProfileImageUrl());
                    return existingUser;
                })
                .orElseGet(() -> createNewUser(profile));


        String accessToken = jwtUtil.createJwt(null, user.getEmail(), user.getRole().toString(), ACCESS_TOKEN_VALIDITY);
        String refreshToken = jwtUtil.createJwt(null, user.getEmail(), user.getRole().toString(), REFRESH_TOKEN_VALIDITY);

        redisUtil.setDataExpire("RT:" + user.getEmail(), refreshToken, REFRESH_TOKEN_VALIDITY);

        return new AuthResponseDTO(user, accessToken, refreshToken);
    }


    private User createNewUser(OAuthProfile profile) {
        return userRepository.save(
                User.builder()
                        .email(profile.getEmail())
                        .nickname(profile.getNickname())
                        .profileImageUrl(profile.getProfileImageUrl())
                        .providerInfo(profile.getProviderInfo())
                        .role(RoleType.USER)
                        .isDormant(false)
                        .build()
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

        String newAccessToken = jwtUtil.createJwt(null, user.getEmail(), user.getRole().toString(), ACCESS_TOKEN_VALIDITY);
        String newRefreshToken = jwtUtil.createJwt(null, user.getEmail(), user.getRole().toString(), REFRESH_TOKEN_VALIDITY);

        redisUtil.deleteData(redisKey);
        redisUtil.setDataExpire(redisKey, newRefreshToken, REFRESH_TOKEN_VALIDITY);

        return new AuthResponseDTO(user, newAccessToken, newRefreshToken);
    }
}