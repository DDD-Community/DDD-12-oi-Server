package com.ddd.oi.schedule.controller.docs;

import com.ddd.oi.common.response.CustomApiResponse;
import com.ddd.oi.schedule.dto.request.CreateScheduleRequest;
import com.ddd.oi.schedule.dto.request.UpdateScheduleRequest;
import com.ddd.oi.schedule.dto.response.CreateScheduleResponse;
import com.ddd.oi.schedule.dto.response.ScheduleListResponse;
import com.ddd.oi.schedule.dto.response.UpdateScheduleResponse;
import com.ddd.oi.user.domain.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "스케줄 컨트롤러", description = "스케줄 관련 API입니다.")
public interface ScheduleControllerDocs {

	@Operation(summary = "일정 추가", description = "일정 추가 API")
	CustomApiResponse<CreateScheduleResponse> createSchedule(
		@AuthenticationPrincipal User user,
		@RequestBody CreateScheduleRequest request);

	@Operation(summary = "일정 삭제", description = "일정 삭제 API")
	CustomApiResponse<Boolean> deleteSchedule(
		@AuthenticationPrincipal User user,
		@PathVariable("scheduleId") Long scheduleId);

	@Operation(summary = "일정 수정", description = "일정 수정 API")
	CustomApiResponse<UpdateScheduleResponse> updateSchedule(
		@AuthenticationPrincipal User user,
		@PathVariable("scheduleId") Long scheduleId,
		@RequestBody UpdateScheduleRequest request);

	@Operation(summary = "특정 날짜 일정 조회", description = "특정 날짜 일정 조회 API")
	CustomApiResponse<List<ScheduleListResponse>> showTargetDaySchedule(
		@AuthenticationPrincipal User user,
		@PathVariable("target-day") LocalDate targetDay);

	@Operation(summary = "한달 일정 조회", description = "한달 일정 조회 API")
	CustomApiResponse<List<ScheduleListResponse>> showMonthSchedule(
		@AuthenticationPrincipal User user,
		@PathVariable("year") int year,
		@PathVariable("month") int month);

	@Operation(summary = "주간 일정 조회", description = "주간 일정 조회 API", parameters = {
		@Parameter(name = "from", description = "조회 시작일 (yyyy-MM-dd)", required = true, example = "2025-08-09"),
		@Parameter(name = "to", description = "조회 종료일 (yyyy-MM-dd)", required = true, example = "2025-08-16")}, responses = @ApiResponse(responseCode = "200", description = "주간 일정 조회 성공", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "성공 응답 예시", value = "{\n  \"statusCode\": 200,\n  \"resultType\": \"SUCCESS\",\n  \"data\": [\n    {\n      \"scheduleId\": 36,\n      \"scheduleTag\": \"DATE\",\n      \"title\": \"일정상세 테스트_근\",\n      \"startDate\": \"2025-09-08\",\n      \"endDate\": \"2025-09-08\",\n      \"mobility\": \"CAR\",\n      \"groups\": [\"SOLO\", \"FRIEND\", \"PARENTS\", \"SIBLINGS\", \"COUPLE\"]\n    },\n    {\n      \"scheduleId\": 37,\n      \"scheduleTag\": \"DAILY\",\n      \"title\": \"연속 일정 테스트_근\",\n      \"startDate\": \"2025-09-15\",\n      \"endDate\": \"2025-09-17\",\n      \"mobility\": \"CAR\",\n      \"groups\": [\"SOLO\"]\n    }\n  ],\n  \"message\": \"주간 일정 조회 성공\"\n}"))))
	CustomApiResponse<List<ScheduleListResponse>> showWeeklySchedule(
		@AuthenticationPrincipal User user,
		@RequestParam("from") LocalDate from,
		@RequestParam("to") LocalDate to);
}
