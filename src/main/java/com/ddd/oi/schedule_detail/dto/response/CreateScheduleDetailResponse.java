package com.ddd.oi.schedule_detail.dto.response;

import com.ddd.oi.common.annotation.NotBlankNullable;
import com.ddd.oi.schedule_detail.domain.ScheduleDetail;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;

@Builder
public record CreateScheduleDetailResponse(
        Long scheduleDetailId,
        @Schema(type = "string", format = "time", pattern = "HH:mm", example = "11:30", description = "시작 시간 (HH:mm)") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime startTime,

        @NotBlankNullable(message = "날짜를 정해주세요.") @JsonFormat(pattern = "yyyy-MM-dd") LocalDate targetDate,
        String memo,

        @NotBlankNullable(message = "장소명을 입력해주세요.") String spotName,

        @NotBlankNullable(message = "위도를 입력해주세요.") Double latitude,

        @NotBlankNullable(message = "경도를 입력해주세요.") Double longitude,
        String category) {
    public static CreateScheduleDetailResponse of(ScheduleDetail scheduleDetail) {
        return CreateScheduleDetailResponse.builder()
                .scheduleDetailId(scheduleDetail.getId())
                .startTime(scheduleDetail.getStartTime())
                .targetDate(scheduleDetail.getTargetDate())
                .memo(scheduleDetail.getMemo())
                .spotName(scheduleDetail.getSpotName())
                .latitude(scheduleDetail.getLatitude())
                .longitude(scheduleDetail.getLongitude())
                .category(scheduleDetail.getCategory())
                .build();
    }
}
