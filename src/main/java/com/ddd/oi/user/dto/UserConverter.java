package com.ddd.oi.user.dto;

import com.ddd.oi.user.domain.User;

public class UserConverter {
    public static UserResponseDTO.JoinResultDTO toJoinResultDTO(User user,String accessToken,String refreshToken) {
        return new UserResponseDTO.JoinResultDTO(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                user.getProfileUrl(),
                user.getProviderInfo(),
                user.getRole(),
                accessToken,
                refreshToken
        );
    }
}

