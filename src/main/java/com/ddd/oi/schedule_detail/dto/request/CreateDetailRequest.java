package com.ddd.oi.schedule_detail.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CreateDetailRequest(
	@NotNull
	@JsonFormat(pattern = "HH:mm")
	@Schema(type = "string", format = "time", pattern = "HH:mm", example = "14:30")
	LocalTime startTime,

	String memo,
	@NotNull
	LocalDate targetDate,

	@NotNull
	String spotName,

	@NotNull
	Double latitude,

	@NotNull
	Double longitude
) {
}
