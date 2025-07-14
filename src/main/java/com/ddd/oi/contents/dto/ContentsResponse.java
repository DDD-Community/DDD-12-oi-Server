package com.ddd.oi.contents.dto;

import com.ddd.oi.contents.domain.Contents;
import com.ddd.oi.contents.domain.enumType.ContentsTag;

import lombok.Builder;

import java.util.List;

@Builder
public record ContentsResponse(
        Long id,
        String title,
        String displayDescription,
        Integer cost,
        String recommendedSchedule,
        Integer duration,
        ContentsTag contentsTag,
        String shortTitle,
        String shortDescription,
        List<Long> imageIds,
        List<Long> spotIds) {
    public static ContentsResponse from(Contents contents) {
        return new ContentsResponse(
                contents.getId(),
                contents.getTitle(),
                contents.getDisplayDescription(),
                contents.getCost(),
                contents.getRecommendedSchedule(),
                contents.getDuration(),
                contents.getContentsTag(),
                contents.getShortTitle(),
                contents.getShortDescription(),
                contents.getImages().stream().map(img -> img.getContentsImageId()).toList(),
                contents.getSpots().stream().map(spot -> spot.getContentsSpotId()).toList());
    }
}