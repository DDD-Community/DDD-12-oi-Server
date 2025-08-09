package com.ddd.oi.faq.dto;

import com.ddd.oi.faq.domain.Faq;
import lombok.Builder;

@Builder
public record FaqResponse(
    String title,
    String content
) {
    public static FaqResponse from(Faq faq) {
        return FaqResponse.builder()
            .title(faq.getTitle())
            .content(faq.getContent())
            .build();
    }
}
