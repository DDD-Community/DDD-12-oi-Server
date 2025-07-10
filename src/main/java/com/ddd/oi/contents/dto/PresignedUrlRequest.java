package com.ddd.oi.contents.dto;

public record PresignedUrlRequest(
        String fileName,
        String contentType) {
}