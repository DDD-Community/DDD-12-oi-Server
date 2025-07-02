package com.ddd.oi.schedule_detail.dto.request;

import com.ddd.oi.common.annotation.NotBlankNullable;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record UpdateDetailRequest(
	@NotBlankNullable(message = "시작 시간을 정해주세요.")
	@JsonFormat(pattern = "HH:mm") LocalTime startTime,

	@NotBlankNullable(message = "날짜를 정해주세요.")
	LocalDate targetDate,
	String memo,

	@NotBlankNullable(message = "장소명을 입력해주세요.")
	String spotName,

	@NotBlankNullable(message = "위도를 입력해주세요.")
	Double latitude,

	@NotBlankNullable(message = "경도를 입력해주세요.")
	Double longitude) {
}
