package com.ddd.oi.contents.dto;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.contents.domain.Contents;
import com.ddd.oi.contents.domain.enumType.ContentsTag;

import java.util.List;

public record ContentsCreateRequest(
        String title,
        String displayDescription,
        Integer cost,
        String recommendedSchedule,
        Integer duration,
        ContentsTag contentsTag,
        String shortTitle,
        String shortDescription,
        String contentsImage,
        Double recommendationScore
        ) {
    private static final String IMAGE_BASE_URL = "https://ddd-oi.store/api/v1/s3/images/";
    public ContentsCreateRequest {
        if (title == null || title.isBlank())
            throw new OiException(ErrorCode.PARAMETER_INVALID);
        if (contentsTag == null)
            throw new OiException(ErrorCode.PARAMETER_INVALID);
        if (cost != null && cost < 0)
            throw new OiException(ErrorCode.PARAMETER_INVALID);
        if (duration != null && duration < 0)
            throw new OiException(ErrorCode.PARAMETER_INVALID);
    }

    public Contents toEntity() {
        return Contents.builder()
            .title(this.title())
            .displayDescription(this.displayDescription())
            .cost(this.cost())
            .recommendedSchedule(this.recommendedSchedule())
            .duration(this.duration())
            .contentsTag(this.contentsTag())
            .shortTitle(this.shortTitle())
            .shortDescription(this.shortDescription())
            .contentsImage(IMAGE_BASE_URL + this.contentsImage())
            .recommendationScore(this.recommendationScore())
            .build();
    }
}
