package com.ddd.oi.auth.dto;

import com.ddd.oi.user.domain.ProviderInfo;

public record KakaoProfileAdapter(KakaoDTO.KakaoProfile profile) implements OAuthProfile {

    @Override
    public String getEmail() {
        return profile.getKakao_account().getEmail();
    }

    @Override
    public String getNickname() {
        return profile.getKakao_account().getProfile().getNickname();
    }

//    @Override
//    public String getProfileImageUrl() {
//        return profile.getKakao_account().getProfile().getProfile_image_url();
//    }

    @Override
    public ProviderInfo getProviderInfo() {
        return ProviderInfo.KAKAO;
    }
}
