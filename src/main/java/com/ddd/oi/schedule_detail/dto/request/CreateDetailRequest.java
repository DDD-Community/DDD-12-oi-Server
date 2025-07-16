package com.ddd.oi.schedule_detail.dto.request;

import java.time.LocalDate;

import com.ddd.oi.common.annotation.NotBlankNullable;
import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.schedule.domain.Schedule;
import com.ddd.oi.schedule_detail.domain.ScheduleDetail;

public record CreateDetailRequest(

		@NotBlankNullable(message = "날짜를 정해주세요.") LocalDate targetDate,
		String memo,

		@NotBlankNullable(message = "장소명을 입력해주세요.") String spotName,

		@NotBlankNullable(message = "위도를 입력해주세요.") Double latitude,

		@NotBlankNullable(message = "경도를 입력해주세요.") Double longitude,

		@NotBlankNullable(message = "카테고리를 입력해주세요.") String category) {
	public CreateDetailRequest {
		if (latitude < -90 || latitude > 90) {
			throw new OiException(ErrorCode.INVALID_LATITUDE);
		}
		if (longitude < -180 || longitude > 180) {
			throw new OiException(ErrorCode.INVALID_LONGITUDE);
		}
		if (targetDate != null) {
			if (targetDate.isBefore(LocalDate.now())) {
				throw new OiException(ErrorCode.INVALID_TARGET_DATE);
			}
		}
	}

	public ScheduleDetail toEntity(Schedule schedule, String mainCategory) {
		return ScheduleDetail.builder()
				.startTime(null)
				.targetDate(this.targetDate())
				.memo(this.memo())
				.spotName(this.spotName())
				.latitude(this.latitude())
				.longitude(this.longitude())
				.category(mainCategory)
				.schedule(schedule)
				.build();
	}
}
