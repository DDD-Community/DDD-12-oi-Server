package com.ddd.oi.contents_image.dto;

import com.ddd.oi.contents_image.domain.ContentsImage;
import lombok.Builder;

@Builder
public record ContentsImageResponse(
        Long id,
        String imageUrl) {
    public static ContentsImageResponse from(ContentsImage contentsImage) {
        return ContentsImageResponse.builder()
                .id(contentsImage.getId())
                .imageUrl(contentsImage.getImageUrl())
                .build();
    }
}