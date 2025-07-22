package com.ddd.oi.user.service;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.common.utils.GoogleUtil;
import com.ddd.oi.common.utils.KakaoUtil;
import com.ddd.oi.common.utils.NaverUtil;
import com.ddd.oi.user.domain.User;
import com.ddd.oi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final KakaoUtil kakaoUtil;
    private final NaverUtil naverUtil;
    private final GoogleUtil googleUtil;

    @Transactional
    public Boolean markUserAsDormant(User user, String oauthAccessToken) {
        if (user.getIsDormant() == true) {
            throw new OiException(ErrorCode.ALREADY_DELETED_USER);
        }
        userRepository.delete(user);
        oauthUnlink(user, oauthAccessToken);
        return true;
    }

    private void oauthUnlink(User user, String oauthAccessToken) {
        switch (user.getProviderInfo()) {
            case KAKAO -> {
                String kakaoUserId = kakaoUtil.getKakaoUserId(oauthAccessToken);
                kakaoUtil.unlink(kakaoUserId);
            }
            case NAVER -> {
                 naverUtil.unlink(oauthAccessToken, user.getEmail());
            }
            case GOOGLE -> {
                googleUtil.unlink(oauthAccessToken, user.getEmail());
            }
            default -> throw new OiException(ErrorCode.BAD_REQUEST);
        }
    }
}

