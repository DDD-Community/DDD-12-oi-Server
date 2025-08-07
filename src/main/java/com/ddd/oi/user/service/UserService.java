package com.ddd.oi.user.service;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.common.utils.GoogleUtil;
import com.ddd.oi.common.utils.KakaoUtil;
import com.ddd.oi.common.utils.NaverUtil;
import com.ddd.oi.user.domain.User;
import com.ddd.oi.user.dto.request.UpdateNicknameRequest;
import com.ddd.oi.user.dto.response.UpdateNicknameResponse;
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
    public Boolean deleteUser(User user, String oauthAccessToken) {

        User persistedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));
        oauthUnlink(persistedUser, oauthAccessToken);
        userRepository.delete(persistedUser);
        return true;
    }

    @Transactional
    public UpdateNicknameResponse updateNickname(User user, UpdateNicknameRequest request) {
        User persistedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));
        persistedUser.updateNickname(request.nickname());
        return UpdateNicknameResponse.of(persistedUser.getNickname());
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

