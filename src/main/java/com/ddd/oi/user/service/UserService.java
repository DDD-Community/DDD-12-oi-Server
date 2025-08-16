package com.ddd.oi.user.service;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.common.utils.GoogleUtil;
import com.ddd.oi.common.utils.KakaoUtil;
import com.ddd.oi.common.utils.NaverUtil;
import com.ddd.oi.notice.domain.Notice;
import com.ddd.oi.notice.repository.NoticeRepository;
import com.ddd.oi.user.domain.User;
import com.ddd.oi.user.dto.request.UpdateNicknameRequest;
import com.ddd.oi.user.dto.response.ShowUserResponse;
import com.ddd.oi.user.dto.response.UpdateNicknameResponse;
import com.ddd.oi.user.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final KakaoUtil kakaoUtil;
    private final NaverUtil naverUtil;
    private final GoogleUtil googleUtil;
    private final NoticeRepository noticeRepository;
    private final ObjectProvider<BuildProperties> buildProps;

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

        @Transactional(readOnly = true)
        public ShowUserResponse showUserAndSystemInfo(Long userId) {
//        User persistedUser = userRepository.findById(user.getId())
//                .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));
            User persistedUser = userRepository.findById(userId)
                    .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));

            LocalDateTime latestNoticeUpdateAt = noticeRepository.findTopByOrderByUpdatedAtDesc()
                    .map(Notice::getUpdatedAt)
                    .orElse(null);

            String version = java.util.Optional.ofNullable(buildProps.getIfAvailable())
                    .map(BuildProperties::getVersion)
                    .orElse("unknown");

            ShowUserResponse.UserInfo userInfo = ShowUserResponse.UserInfo.builder()
                    .name(persistedUser.getNickname())
                    .email(persistedUser.getEmail())
                    .providerInfo(persistedUser.getProviderInfo())
                    .build();

            ShowUserResponse.SystemInfo systemInfo = ShowUserResponse.SystemInfo.builder()
                    .updateAt(latestNoticeUpdateAt)
                    .version(version)
                    .build();

            return ShowUserResponse.of(userInfo, systemInfo);
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

