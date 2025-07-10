package com.ddd.oi.contents_spot.dto;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;

public record ContentsSpotRequest(
        String spotName,
        String address,
        String spotDescription,
        String spotImage,
        Double latitude,
        Double longitude) {
    public ContentsSpotRequest {
        if (spotName == null || spotName.isBlank())
            throw new OiException(ErrorCode.PARAMETER_INVALID);
        if (latitude != null && (latitude < -90 || latitude > 90))
            throw new OiException(ErrorCode.INVALID_LATITUDE);
        if (longitude != null && (longitude < -180 || longitude > 180))
            throw new OiException(ErrorCode.INVALID_LONGITUDE);
    }
}