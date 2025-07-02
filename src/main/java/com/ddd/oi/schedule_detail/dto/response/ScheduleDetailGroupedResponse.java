package com.ddd.oi.schedule_detail.dto.response;

import com.ddd.oi.schedule_detail.domain.ScheduleDetail;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;

@Builder
public record ScheduleDetailGroupedResponse(
	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate targetDate,
	List<ScheduleDetailResponse> details
) {
	public static ScheduleDetailGroupedResponse from(LocalDate targetDate, List<ScheduleDetail> details) {
		return new ScheduleDetailGroupedResponse(
			targetDate,
			details.stream().map(ScheduleDetailResponse::from).toList()
		);
	}
}
