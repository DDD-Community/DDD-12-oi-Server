package com.ddd.oi.schedule.dto.response;

import com.ddd.oi.schedule.domain.Schedule;
import com.ddd.oi.schedule.domain.enumType.Mobility;
import com.ddd.oi.schedule.domain.enumType.ScheduleTag;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@Builder
public record CreateScheduleResponse(
    Long scheduleId,
    Long userId,
    String title,
    LocalDate startDate,
    LocalDate endDate,
    Mobility mobility,
    List<String> groups,
    ScheduleTag scheduleTag
) {

    public static CreateScheduleResponse of(Schedule schedule) {
        return CreateScheduleResponse.builder()
            .scheduleId(schedule.getId())
            .userId(schedule.getUser().getId())
            .title(schedule.getScheduleTitle())
            .startDate(schedule.getStartDate())
            .endDate(schedule.getEndDate())
            .mobility(schedule.getMobility())
            .groups(schedule.getGroups().stream().map(Enum::name).toList())
            .scheduleTag(schedule.getScheduleTag())
            .build();
    }
}
