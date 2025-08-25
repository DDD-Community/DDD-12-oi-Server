package com.ddd.oi.auth.service;

import com.ddd.oi.auth.dto.AuthResponse;
import com.ddd.oi.auth.service.adapter.GoogleProfileAdapter;
import com.ddd.oi.auth.service.adapter.KakaoProfileAdapter;
import com.ddd.oi.auth.service.adapter.NaverProfileAdapter;
import com.ddd.oi.auth.dto.profile.OAuthProfile;
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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public AuthResponse oAuthLogin(ProviderInfo provider, String oauthAccessToken, HttpServletResponse response) {
        OAuthProfile profile;

        switch (provider) {
            case KAKAO -> {
                var kakaoProfile = kakaoUtil.requestProfileByAccessToken(oauthAccessToken);
                profile = new KakaoProfileAdapter(kakaoProfile);

                User user = userRepository.findByProviderInfoAndProviderId(ProviderInfo.KAKAO, String.valueOf(kakaoProfile.getId()))
                        .map(existingUser -> {
                            existingUser.updateNickname(profile.getNickname());
                            existingUser.updateEmail(profile.getEmail());
                            return existingUser;
                        })
                        .orElseGet(() -> createNewUserWithProviderId(profile, kakaoProfile.getId()));

                String accessToken = jwtUtil.createJwt(null, user.getEmail(), user.getRole().toString(), ACCESS_TOKEN_VALIDITY);
                String refreshToken = jwtUtil.createJwt(null, user.getEmail(), user.getRole().toString(), REFRESH_TOKEN_VALIDITY);
                redisUtil.setDataExpire("RT:" + user.getEmail(), refreshToken, REFRESH_TOKEN_VALIDITY);

                return new AuthResponse(user, accessToken, refreshToken, oauthAccessToken);
            }
            case NAVER -> {
                var naverProfile = naverUtil.requestProfileByAccessToken(oauthAccessToken);
                profile = new NaverProfileAdapter(naverProfile);
            }
            case GOOGLE -> {
                var googleProfile = googleUtil.requestProfileByAccessToken(oauthAccessToken);
                profile = new GoogleProfileAdapter(googleProfile);
            }
            default -> throw new OiException(ErrorCode.BAD_REQUEST);
        }

        User user = userRepository.findByEmail(profile.getEmail())
                .map(existingUser -> {
                    existingUser.updateNickname(profile.getNickname());
                    existingUser.updateEmail(profile.getEmail());
                    return existingUser;
                })
                .orElseGet(() -> createNewUser(profile));

        String accessToken = jwtUtil.createJwt(null, user.getEmail(), user.getRole().toString(), ACCESS_TOKEN_VALIDITY);
        String refreshToken = jwtUtil.createJwt(null, user.getEmail(), user.getRole().toString(), REFRESH_TOKEN_VALIDITY);
        redisUtil.setDataExpire("RT:" + user.getEmail(), refreshToken, REFRESH_TOKEN_VALIDITY);

        return new AuthResponse(user, accessToken, refreshToken, oauthAccessToken);
    }

    private User createNewUserWithProviderId(OAuthProfile profile, String kakaoId) {
        return userRepository.save(
                User.builder()
                        .providerInfo(profile.getProviderInfo())
                        .providerId(kakaoId)
                        .email(profile.getEmail())
                        .nickname(profile.getNickname())
                        .role(RoleType.USER)
                        .build()
        );
    }

    private User createNewUser(OAuthProfile profile) {
        return userRepository.save(
                User.builder()
                        .email(profile.getEmail())
                        .nickname(profile.getNickname())
//                        .profileImageUrl(profile.getProfileImageUrl())
                        .providerInfo(profile.getProviderInfo())
                        .role(RoleType.USER)
                        .build()
        );
    }

    public AuthResponse reissueAccessToken(String oldRefreshToken, HttpServletResponse response) {
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

        return new AuthResponse(user, newAccessToken, newRefreshToken,null);
    }
    public Boolean logout(ProviderInfo provider, String oauthAccessToken,String userEmail) {
        switch (provider) {
            case KAKAO -> {
                kakaoUtil.logoutKakao(oauthAccessToken);
                redisUtil.deleteData("RT:" + userEmail);
                return true;
            }
            case NAVER -> {
                naverUtil.logoutNaver(userEmail);
                return true;
            }
            case GOOGLE -> {
                googleUtil.logoutGoogle(oauthAccessToken,userEmail);
                return false;
            }
            default -> throw new OiException(ErrorCode.BAD_REQUEST);
        }
    }

}