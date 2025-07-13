package com.ddd.oi.schedule_detail.dto;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.schedule_detail.dto.request.CreateDetailRequest;
import com.ddd.oi.schedule_detail.dto.request.UpdateDetailRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScheduleDetailRequestTest {

	@Test
	@DisplayName("CreateDetailRequest - 위도가 범위를 벗어나면 예외 발생")
	void 위도값_범위_초과시_예외발생() {
		OiException ex = assertThrows(OiException.class, () ->
			new CreateDetailRequest(
				LocalDate.of(2026, 5, 3),
				"메모",
				"장소",
				100.0,
				127.0
			)
		);
		assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.INVALID_LATITUDE);
	}

	@Test
	@DisplayName("CreateDetailRequest - 경도가 범위를 벗어나면 예외 발생")
	void 경도값_범위_초과시_예외발생() {
		OiException ex = assertThrows(OiException.class, () ->
			new CreateDetailRequest(
				LocalDate.of(2026, 5, 3),
				"메모",
				"장소",
				37.5,
				200.0
			)
		);
		assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.INVALID_LONGITUDE);
	}
	@Test
	@DisplayName("CreateDetailRequest - targetDate가 현재보다 과거인 경우 예외 발생")
	void 현재보다_과거인_경우_예외_발생() {

		OiException ex = assertThrows(OiException.class, () ->
			new CreateDetailRequest(
				LocalDate.of(2024, 5, 3),
				"메모",
				"장소",
				37.5,
				127.0
			)
		);
		assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.INVALID_TARGET_DATE);
	}

	@Test
	@DisplayName("UpdateDetailRequest - 위도가 범위를 벗어나면 예외 발생")
	void 위도값_초과시_예외() {
		OiException ex = assertThrows(OiException.class, () ->
			new UpdateDetailRequest(
				LocalTime.NOON,
				LocalDate.of(2026, 5, 5),
				"메모",
				"장소",
				-100.0,
				126.0
			)
		);
		assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.INVALID_LATITUDE);
	}

	@Test
	@DisplayName("UpdateDetailRequest - 경도가 범위를 벗어나면 예외 발생")
	void 경도_예외() {
		OiException ex = assertThrows(OiException.class, () ->
			new UpdateDetailRequest(
				LocalTime.NOON,
				LocalDate.of(2026, 5, 5),
				"메모",
				"장소",
				36.0,
				-200.0
			)
		);
		assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.INVALID_LONGITUDE);
	}
}
