package com.ddd.oi.auth.service.adapter;

import com.ddd.oi.auth.dto.profile.OAuthProfile;
import com.ddd.oi.auth.dto.profile.GoogleDTO;
import com.ddd.oi.user.domain.ProviderInfo;

public record GoogleProfileAdapter(GoogleDTO.GoogleProfile profile) implements OAuthProfile {
    @Override
    public String getEmail() {
        return profile.getEmail();
    }
    @Override
    public String getNickname() {
        return profile.getName();
    }
//    @Override
//    public String getProfileImageUrl() {
//        return profile.getPicture();
//    }
    @Override
    public ProviderInfo getProviderInfo() {
        return ProviderInfo.GOOGLE;
    }


}
