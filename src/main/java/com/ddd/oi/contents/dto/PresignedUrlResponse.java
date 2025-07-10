package com.ddd.oi.contents.dto;

import lombok.Builder;

@Builder
public record PresignedUrlResponse(
        String presignedUrl,
        String uploadUrl) {
}