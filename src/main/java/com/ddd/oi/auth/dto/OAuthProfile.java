package com.ddd.oi.auth.dto;

import com.ddd.oi.user.domain.ProviderInfo;

public interface OAuthProfile {
    String getEmail();
    String getNickname();
//    String getProfileImageUrl();
    ProviderInfo getProviderInfo();
}

