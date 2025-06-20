package com.ddd.oi.schedule.dto.response;

import com.ddd.oi.schedule.domain.Schedule;
import com.ddd.oi.schedule.domain.enumType.Mobility;
import com.ddd.oi.schedule.domain.enumType.ScheduleTag;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;

@Builder
public record UpdateScheduleResponse(
        Long id,
        String title,
        LocalDate startDate,
        LocalDate endDate,
        Mobility mobility,
        List<String> groups,
        ScheduleTag scheduleTag) {
    public static UpdateScheduleResponse of(Schedule schedule) {
        return UpdateScheduleResponse.builder()
                .id(schedule.getId())
                .title(schedule.getScheduleTitle())
                .startDate(schedule.getStartDate())
                .endDate(schedule.getEndDate())
                .mobility(schedule.getMobility())
                .groups(schedule.getGroups().stream().map(Enum::name).collect(Collectors.toList()))
                .scheduleTag(schedule.getScheduleTag())
                .build();
    }
}
