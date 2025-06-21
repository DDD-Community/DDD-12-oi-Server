package com.ddd.oi.schedule.dto.request;

import com.ddd.oi.common.annotation.NotBlankNullable;
import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.schedule.domain.enumType.GroupTag;
import com.ddd.oi.schedule.domain.enumType.Mobility;
import com.ddd.oi.schedule.domain.enumType.ScheduleTag;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record UpdateScheduleRequest(
        @Schema(description = "일정 제목", example = "여름휴가") String title,

        @Schema(description = "시작 날짜", example = "2025-07-01") LocalDate startDate,

        @Schema(description = "종료 날짜", example = "2024-07-02") LocalDate endDate,

        @Schema(description = "이동 수단", example = "CAR", allowableValues = {
                "WALK", "CAR", "PUBLIC_TRANSPORT", "BICYCLE" }) Mobility mobility,
        @NotBlankNullable(message = "태그를 정해주세요.")
        ScheduleTag scheduleTag,

        @Schema(description = "일행 태그 리스트", example = "[\"SOLO\", \"SIBLINGS\"]", allowableValues = { "SOLO", "COUPLE",
                "FRIEND", "PARENTS", "SIBLINGS", "CHILDREN", "PET", "OTHER" }) List<String> groups){
    public UpdateScheduleRequest {
        if (startDate != null && endDate != null) {
            if (endDate.isBefore(startDate)) {
                throw new OiException(ErrorCode.END_DATE_BEFORE_START_DATE);
            }
        }
        if (groups != null && groups.size() != groups.stream().distinct().count()) {
            throw new OiException(ErrorCode.DUPLICATE_GROUP_NAME);
        }
    }

    public List<GroupTag> toGroupsEnum() {
        if (this.groups == null) {
            return null;
        }
        return this.groups.stream()
                .map(String::toUpperCase)
                .map(GroupTag::valueOf)
                .collect(Collectors.toList());
    }
}
