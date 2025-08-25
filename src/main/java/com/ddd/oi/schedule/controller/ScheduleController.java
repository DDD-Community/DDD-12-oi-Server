package com.ddd.oi.schedule.controller;

import com.ddd.oi.common.response.CustomApiResponse;
import com.ddd.oi.schedule.controller.docs.ScheduleControllerDocs;
import com.ddd.oi.schedule.dto.request.CreateScheduleRequest;
import com.ddd.oi.schedule.dto.request.UpdateScheduleRequest;
import com.ddd.oi.schedule.dto.response.CreateScheduleResponse;
import com.ddd.oi.schedule.dto.response.ScheduleListResponse;
import com.ddd.oi.schedule.dto.response.UpdateScheduleResponse;
import com.ddd.oi.schedule.service.ScheduleService;
import com.ddd.oi.user.domain.User;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
@Tag(name = "스케줄 컨트롤러", description = "스케줄 관련 API입니다.")
public class ScheduleController implements ScheduleControllerDocs {
	private final ScheduleService scheduleService;

	@PostMapping
	@Override
	public CustomApiResponse<CreateScheduleResponse> createSchedule(@AuthenticationPrincipal User user,
		@RequestBody CreateScheduleRequest request) {
		CreateScheduleResponse result = scheduleService.createSchedule(user.getId(), request);
		return CustomApiResponse.success(result, 200, "스케줄 생성 성공");
	}

	@DeleteMapping("/{scheduleId}")
	@Override
	public CustomApiResponse<Boolean> deleteSchedule(@AuthenticationPrincipal User user,
		@PathVariable("scheduleId") Long scheduleId) {
		Boolean result = scheduleService.deleteSchedule(user.getId(), scheduleId);
		return CustomApiResponse.success(result, 200, "일정 삭제 성공");
	}


	@PutMapping("/{scheduleId}")
	@Override
	public CustomApiResponse<UpdateScheduleResponse> updateSchedule(
		@AuthenticationPrincipal User user,
		@PathVariable("scheduleId") Long scheduleId,
		@RequestBody UpdateScheduleRequest request
	) {
		UpdateScheduleResponse result = scheduleService.updateSchedule(user.getId(), scheduleId, request);
		return CustomApiResponse.success(result, 200, "스케줄 수정 성공");
	}

	@GetMapping("/{target-day}")
	@Override
	public CustomApiResponse<List<ScheduleListResponse>> showTargetDaySchedule(
		@AuthenticationPrincipal User user,
		@PathVariable("target-day") LocalDate targetDay
	) {
		List<ScheduleListResponse> result = scheduleService.showTargetDaySchedule(user.getId(), targetDay);
		return CustomApiResponse.success(result, 200, "해당 날짜의 일정들 조회 성공");
	}

	@GetMapping("/{year}/{month}")
	@Override
	public CustomApiResponse<List<ScheduleListResponse>> showMonthSchedule(
		@AuthenticationPrincipal User user,
		@PathVariable("year") int year,
		@PathVariable("month") int month
	) {
		List<ScheduleListResponse> result = scheduleService.showMonthScheduleList(user.getId(), year, month);
		return CustomApiResponse.success(result, 200, "해당 월의 일정들 조회 성공");
	}

	@GetMapping("/week")
	@Override
	public CustomApiResponse<List<ScheduleListResponse>> showWeeklySchedule(
		@AuthenticationPrincipal User user,
		@RequestParam("from") LocalDate from,
		@RequestParam("to") LocalDate to
	) {
		List<ScheduleListResponse> result = scheduleService.showWeeklySchedule(user.getId(), from, to);
		return CustomApiResponse.success(result, 200, "주간 일정 조회 성공");
	}
}
