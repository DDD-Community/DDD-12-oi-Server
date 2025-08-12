package com.ddd.oi.user.dto.response;

import lombok.Builder;

@Builder
public record UpdateNicknameResponse(
        String nickname
) {

    public static UpdateNicknameResponse of(String nickname) {
        return UpdateNicknameResponse.builder()
                .nickname(nickname)
                .build();
    }
}
