package com.ddd.oi.schedule.dto.request;

import com.ddd.oi.common.annotation.NotBlankNullable;
import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.schedule.domain.Schedule;
import com.ddd.oi.schedule.domain.enumType.GroupTag;
import com.ddd.oi.schedule.domain.enumType.Mobility;
import com.ddd.oi.schedule.domain.enumType.ScheduleTag;
import com.ddd.oi.user.domain.User;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.List;

public record CreateScheduleRequest(
	@NotBlankNullable(message = "스케줄 제목은 필수입니다.")
	String title,
	@NotBlankNullable(message = "시작 날짜를 정해주세요.")
	LocalDate startDate,
	@NotBlankNullable(message = "스케줄 제목은 필수입니다.")
	LocalDate endDate,
	@NotBlankNullable(message = "이동수단을 정해주세요.")
	Mobility mobility,
	@NotBlankNullable(message = "태그를 정해주세요.")
	ScheduleTag scheduleTag,
	@NotEmpty(message = "하나 이상의 일행을 정해주세요.")
	List<GroupTag> groups
) {
	public CreateScheduleRequest {
		if (startDate != null && endDate != null) {
			if (endDate.isBefore(startDate)) {
				throw new OiException(ErrorCode.END_DATE_BEFORE_START_DATE);
			}
		}
		if (groups != null && groups.size() != groups.stream().distinct().count()) {
			throw new OiException(ErrorCode.BAD_REQUEST);
		}
	}

	public Schedule toEntity(User user) {

		return Schedule.builder()
			.user(user)
			.scheduleTitle(this.title)
			.startDate(this.startDate)
			.endDate(this.endDate)
			.mobility(this.mobility)
			.scheduleTag(this.scheduleTag)
			.groups(this.groups)
			.build();
	}
}
