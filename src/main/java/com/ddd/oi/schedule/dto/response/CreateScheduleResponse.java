package com.ddd.oi.schedule.dto.response;

import lombok.Builder;

@Builder
public record CreateScheduleResponse(
        Long scheduleId
) {
    public static CreateScheduleResponse of(Long scheduleId) {
        return CreateScheduleResponse.builder()
                .scheduleId(scheduleId)
                .build();
    }
}
