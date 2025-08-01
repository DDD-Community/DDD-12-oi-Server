package com.ddd.oi.auth.service.adapter;

import com.ddd.oi.auth.dto.profile.OAuthProfile;
import com.ddd.oi.auth.dto.profile.NaverDTO;
import com.ddd.oi.user.domain.ProviderInfo;

public record NaverProfileAdapter(NaverDTO.NaverProfile profile) implements OAuthProfile {

    @Override
    public String getEmail() {
        return profile.getResponse().getEmail();
    }
    @Override
    public String getNickname() {
        return profile.getResponse().getNickname();
    }
//    @Override
//    public String getProfileImageUrl() {
//        return profile.getResponse().getProfile_image();
//    }
    @Override
    public ProviderInfo getProviderInfo() {
        return ProviderInfo.NAVER;
    }
}
