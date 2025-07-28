package com.ddd.oi.contents_spot.dto;

import com.ddd.oi.contents_spot.domain.ContentsSpot;

public record ContentsSpotResponse(
        Long id,
        String spotName,
        String address,
        String spotDescription,
        String spotImage,
        Double latitude,
        Double longitude) {
    public static ContentsSpotResponse from(ContentsSpot spot) {
        return new ContentsSpotResponse(
                spot.getId(),
                spot.getSpotName(),
                spot.getAddress(),
                spot.getSpotDescription(),
                spot.getSpotImage(),
                spot.getLatitude(),
                spot.getLongitude());
    }
}