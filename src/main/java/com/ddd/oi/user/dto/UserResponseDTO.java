package com.ddd.oi.user.dto;

import com.ddd.oi.user.domain.ProviderInfo;
import com.ddd.oi.user.domain.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponseDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinResultDTO {
        private Long id;
        private String nickname;
        private String email;
        private String profileUrl;
        private ProviderInfo providerInfo;
        private RoleType role;
        private String accessToken;
        private String refreshToken;
    }
}

