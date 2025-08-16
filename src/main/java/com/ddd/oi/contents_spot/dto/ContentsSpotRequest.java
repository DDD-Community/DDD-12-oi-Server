package com.ddd.oi.contents_spot.dto;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.contents.domain.Contents;
import com.ddd.oi.contents_spot.domain.ContentsSpot;

public record ContentsSpotRequest(
        String spotName,
        String address,
        String spotDescription,
        String spotImage,
        Double latitude,
        Double longitude,
        String category) {
    private static final String IMAGE_BASE_URL = "https://ddd-oi.store/api/v1/s3/images/";

    public ContentsSpotRequest {
        if (spotName == null || spotName.isBlank())
            throw new OiException(ErrorCode.PARAMETER_INVALID);
        if (latitude != null && (latitude < -90 || latitude > 90))
            throw new OiException(ErrorCode.INVALID_LATITUDE);
        if (longitude != null && (longitude < -180 || longitude > 180))
            throw new OiException(ErrorCode.INVALID_LONGITUDE);
    }
    public ContentsSpot toEntity(Contents contents) {
        return ContentsSpot.builder()
            .spotName(this.spotName())
            .address(this.address())
            .spotDescription(this.spotDescription())
            .spotImage(IMAGE_BASE_URL + this.spotImage())
            .latitude(this.latitude())
            .longitude(this.longitude())
            .category(this.category())
            .contents(contents)
            .build();
    }
}
