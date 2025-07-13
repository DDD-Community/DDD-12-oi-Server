package com.ddd.oi.auth.service;

import com.ddd.oi.user.domain.RoleType;
import com.ddd.oi.user.domain.ProviderInfo;
import com.ddd.oi.user.domain.User;

public class AuthConverter {

        public static User toUser(
                String email,
                String nickname,
                String profileImageUrl,
                ProviderInfo providerInfo
        ) {
            return User.builder()
                    .email(email)
                    .nickname(nickname)
                    .profileImageUrl(profileImageUrl)
                    .providerInfo(providerInfo)
                    .role(RoleType.USER)
                    .isDormant(false)
                    .build();
        }
    }

