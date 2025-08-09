package com.ddd.oi.user.dto;

import java.time.LocalDateTime;

import com.ddd.oi.user.domain.ProviderInfo;
import com.ddd.oi.user.domain.RoleType;

public record UserResponseDTO(
        Long id,
        String nickname,
        String email,
//        String profileImageUrl,
        ProviderInfo providerInfo,
        RoleType role,
        String accessToken,
        String refreshToken,
        String oauthAccessToken,
		LocalDateTime lastReadAt
) {


}

