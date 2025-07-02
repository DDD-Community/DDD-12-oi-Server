package com.ddd.oi.schedule_detail.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import com.ddd.oi.schedule_detail.domain.ScheduleDetail;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;

@Builder
public record ScheduleDetailResponse(
	Long id,
	@JsonFormat(pattern = "HH:mm") LocalTime startTime,
	LocalDate targetDate,
	String spotName,
	Double latitude,
	Double longitude,
	String memo) {
	public static ScheduleDetailResponse from(ScheduleDetail entity) {
		return new ScheduleDetailResponse(
			entity.getId(),
			entity.getStartTime(),
			entity.getTargetDate(),
			entity.getSpotName(),
			entity.getLatitude(),
			entity.getLongitude(),
			entity.getMemo());
	}
}
