package com.ddd.oi.notice.dto;

import com.ddd.oi.notice.domain.Notice;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NoticeResponse(
    Long id,
    String title,
    String content,
    LocalDateTime updatedAt
) {
    public static NoticeResponse from(Notice notice) {
        return NoticeResponse.builder()
            .id(notice.getId())
            .title(notice.getTitle())
            .content(notice.getContent())
            .updatedAt(notice.getUpdatedAt())
            .build();
    }
}
