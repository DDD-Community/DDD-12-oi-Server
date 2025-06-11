package com.ddd.oi.schedule.dto.response;

import com.ddd.oi.schedule.domain.Schedule;
import com.ddd.oi.schedule.domain.Schedule.Mobility;
import com.ddd.oi.schedule.domain.Schedule.ScheduleTag;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ScheduleTargetDayResponse(
        Long scheduleId,
        ScheduleTag scheduleTag,
        String title,
        LocalDate startDate,
        LocalDate endDate,
        Mobility mobility
) {
    public static ScheduleTargetDayResponse of(Schedule schedule) {
        return ScheduleTargetDayResponse.builder()
                .scheduleId(schedule.getScheduleId())
                .scheduleTag(schedule.getScheduleTag())
                .title(schedule.getScheduleTitle())
                .startDate(schedule.getStartDate())
                .endDate(schedule.getEndDate())
                .mobility(schedule.getMobility())
                .build();
    }
}
