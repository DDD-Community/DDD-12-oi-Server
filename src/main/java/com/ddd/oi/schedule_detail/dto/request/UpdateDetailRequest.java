package com.ddd.oi.schedule_detail.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record UpdateDetailRequest(
	@NotNull @JsonFormat(pattern = "HH:mm") LocalTime startTime,

	@NotNull LocalDate targetDate,

	String memo,

	@NotNull String spotName,

	@NotNull Double latitude,

	@NotNull Double longitude) {
}
