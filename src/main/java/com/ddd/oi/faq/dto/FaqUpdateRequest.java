package com.ddd.oi.faq.dto;

import jakarta.validation.constraints.NotBlank;

public record FaqUpdateRequest(
    @NotBlank String title,
    @NotBlank String content
) {}
