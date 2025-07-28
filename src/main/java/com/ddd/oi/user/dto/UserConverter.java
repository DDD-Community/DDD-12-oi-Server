package com.ddd.oi.user.dto;

import com.ddd.oi.user.domain.User;

public class UserConverter {
    public static UserResponseDTO toJoinResultDTO(User user, String accessToken, String refreshToken,String oauthAccessToken) {
        return new UserResponseDTO(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
//                user.getProfileImageUrl(),
                user.getProviderInfo(),
                user.getRole(),
                accessToken,
                refreshToken,
                oauthAccessToken
        );
    }
}
