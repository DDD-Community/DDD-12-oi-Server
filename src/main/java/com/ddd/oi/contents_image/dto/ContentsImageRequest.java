package com.ddd.oi.contents_image.dto;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;

public record ContentsImageRequest(
    String imageUrl,
    Long contentsId
) {
    public ContentsImageRequest {
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new OiException(ErrorCode.PARAMETER_INVALID);
        }
        if (contentsId != null && contentsId <= 0) {
            throw new OiException(ErrorCode.PARAMETER_INVALID);
        }
    }
}