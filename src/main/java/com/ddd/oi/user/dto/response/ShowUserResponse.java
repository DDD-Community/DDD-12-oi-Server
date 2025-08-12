package com.ddd.oi.user.dto.response;

import com.ddd.oi.user.domain.ProviderInfo;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ShowUserResponse(
        UserInfo userInfo,
        SystemInfo systemInfo
) {

    @Builder
    public record UserInfo(
            String name,
            String email,
            ProviderInfo providerInfo
    ) {}

    @Builder
    public record SystemInfo(
            LocalDateTime updateAt,
            LocalDateTime lastReadAt,
            String version
    ) {}

    public static ShowUserResponse of(UserInfo userInfo,SystemInfo systemInfo){
        return ShowUserResponse.builder()
                .userInfo(userInfo)
                .systemInfo(systemInfo)
                .build();

    }
}
