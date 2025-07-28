package com.ddd.oi.contents.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PresignedUrlRequest(
                @Schema(description = "업로드할 파일명 (확장자 포함)", example = "test-image.jpg") String fileName,
                @Schema(description = "업로드할 파일의 Content-Type", example = "image/jpeg") String contentType) {
}