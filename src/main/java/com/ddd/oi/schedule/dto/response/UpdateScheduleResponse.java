package com.ddd.oi.schedule.dto.response;

import com.ddd.oi.schedule.domain.Schedule;
import com.ddd.oi.schedule.domain.Schedule.Mobility;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@Builder
public record UpdateScheduleResponse(
        Long scheduleId,
        String title,
        LocalDate startDate,
        LocalDate endDate,
        Mobility mobility,
        List<String> groupList
) {
    public static UpdateScheduleResponse of(Schedule schedule) {
        return UpdateScheduleResponse.builder()
                .scheduleId(schedule.getScheduleId())
                .title(schedule.getScheduleTitle())
                .startDate(schedule.getStartDate())
                .endDate(schedule.getEndDate())
                .mobility(schedule.getMobility())
                .groupList(schedule.getGroupList())
                .build();
    }
}
