package com.ddd.oi.auth.service;

import com.ddd.oi.user.domain.RoleType;
import com.ddd.oi.user.domain.ProviderInfo;
import com.ddd.oi.user.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthConverter {

    //TODO password는 Redis로 ?
        public static User toUser(
                String email,
                String nickname,
                String profileUrl,
                ProviderInfo providerInfo,
                PasswordEncoder passwordEncoder
        ) {
            return User.builder()
                    .email(email)
                    .nickname(nickname)
                    .profileUrl(profileUrl)
                    .providerInfo(providerInfo)
                    .role(RoleType.USER)
                    .isDormant(false)
                    .build();
        }
    }

