package com.ddd.oi.user.domain;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProviderInfo {
    KAKAO("kakao_account", "id"),
    NAVER("response", "id"),
    GOOGLE(null, "sub");

    private final String attributeKey;
    private final String providerCode;
    // TODO 비즈앱 등록 후 email 추가할 예정
//    private final String identifier;

    public static ProviderInfo of(String provider) {
        String upperCastedProvider = provider.toUpperCase();

        return Arrays.stream(ProviderInfo.values())
                .filter(item -> item.name().equals(upperCastedProvider))
                .findFirst()
                .orElseThrow();
    }
}
