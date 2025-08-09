package com.ddd.oi.notice.dto;

import jakarta.validation.constraints.NotBlank;

public record NoticeCreateRequest(
    @NotBlank String title,
    @NotBlank String content
) {}
