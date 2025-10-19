package com.ddd.oi.schedule.dto.response;

import com.ddd.oi.schedule.domain.Schedule;
import com.ddd.oi.schedule.domain.enumType.GroupTag;
import com.ddd.oi.schedule.domain.enumType.Mobility;
import com.ddd.oi.schedule.domain.enumType.ScheduleTag;
import java.time.LocalDate;
import java.util.List;

import lombok.Builder;

@Builder
public record ScheduleListResponse(
    Long scheduleId,
    Long userId,
    ScheduleTag scheduleTag,
    String title,
    LocalDate startDate,
    LocalDate endDate,
    Mobility mobility,
    List<GroupTag> groups) {
    public static ScheduleListResponse of(Schedule schedule) {
        return ScheduleListResponse.builder()
            .scheduleId(schedule.getId())
            .userId(schedule.getUser().getId())
            .scheduleTag(schedule.getScheduleTag())
            .title(schedule.getScheduleTitle())
            .startDate(schedule.getStartDate())
            .endDate(schedule.getEndDate())
            .mobility(schedule.getMobility())
            .groups(schedule.getGroups())
            .build();
    }
}
