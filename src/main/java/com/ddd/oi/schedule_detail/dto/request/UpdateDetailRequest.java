package com.ddd.oi.schedule_detail.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record UpdateDetailRequest(
		@NotNull @JsonFormat(pattern = "HH:mm") @Schema(type = "string", format = "time", pattern = "HH:mm", example = "14:30", description = "시작 시간") LocalTime startTime,

		@NotNull @Schema(description = "날짜", example = "2025-07-01") LocalDate targetDate,

		@Schema(description = "메모", example = "아침 식사 후 출발") String memo,

		@NotNull @Schema(description = "장소명", example = "서울역") String spotName,

		@NotNull @Schema(description = "위도", example = "37.554722") Double latitude,

		@NotNull @Schema(description = "경도", example = "126.970833") Double longitude) {
}
