package com.ddd.oi.auth.dto;

import com.ddd.oi.user.domain.ProviderInfo;

public record GoogleProfileAdapter(GoogleDTO.GoogleProfile profile) implements OAuthProfile{
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
