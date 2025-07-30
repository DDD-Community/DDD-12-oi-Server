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
        List<Long> spotIds,
        Long viewCount) {
    public static ContentsResponse from(Contents contents) {
        return ContentsResponse.builder()
            .id(contents.getId())
            .title(contents.getTitle())
            .displayDescription(contents.getDisplayDescription())
            .cost(contents.getCost())
            .recommendedSchedule(contents.getRecommendedSchedule())
            .duration(contents.getDuration())
            .contentsTag(contents.getContentsTag())
            .shortTitle(contents.getShortTitle())
            .shortDescription(contents.getShortDescription())
            .imageIds(contents.getImages().stream().map(img -> img.getId()).toList())
            .spotIds(contents.getSpots().stream().map(spot -> spot.getId()).toList())
            .viewCount(contents.getViewCount())
            .build();
    }
}
