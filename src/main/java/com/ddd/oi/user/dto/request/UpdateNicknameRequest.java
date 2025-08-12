package com.ddd.oi.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateNicknameRequest(
        @NotBlank(message = "닉네임은 비어있을 수 없습니다.")
        @Size(min = 1, max = 15, message = "닉네임은 최소 1자, 최대 15자까지 입력 가능합니다.")
        @Pattern(
                regexp = "^[가-힣a-zA-Z0-9]+$",
                message = "닉네임은 한글, 영어, 숫자만 사용 가능하며 공백 및 특수문자는 허용되지 않습니다."
        )
        String nickname
) {

}
