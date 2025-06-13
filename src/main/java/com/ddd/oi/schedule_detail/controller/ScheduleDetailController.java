package com.ddd.oi.schedule_detail.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ddd.oi.common.response.CustomApiResponse;
import com.ddd.oi.schedule_detail.dto.request.CreateDetailRequest;
import com.ddd.oi.schedule_detail.dto.request.UpdateDetailRequest;
import com.ddd.oi.schedule_detail.dto.response.ScheduleDetailResponse;
import com.ddd.oi.schedule_detail.service.ScheduleDetailService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/schedules/{scheduleId}/details")
@RequiredArgsConstructor
@Tag(name = "스케줄 상세 컨트롤러", description = "스케줄 상세 관련 API입니다.")
public class ScheduleDetailController {

	private final ScheduleDetailService scheduleDetailService;

	@GetMapping
	@Operation(summary = "세부일정 목록 조회", description = "세부일정 목록 조회 API")
	public CustomApiResponse<List<ScheduleDetailResponse>> getDetails(
		@PathVariable Long scheduleId,
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate targetDate) {

		List<ScheduleDetailResponse> result = scheduleDetailService.getDetails(scheduleId, targetDate);
		return CustomApiResponse.success(result,200,"세부 일정 목록 조회 성공");
	}

	@PostMapping
	@Operation(summary = "세부일정 생성", description = "세부일정 생성 API")
	public CustomApiResponse<Void> createDetail(
		@PathVariable Long scheduleId,
		@RequestBody CreateDetailRequest request) {

		scheduleDetailService.createDetail(scheduleId, request);
		return CustomApiResponse.success(null,200,"세부 일정 생성 성공");
	}

	@PutMapping("/{detailId}")
	@Operation(summary = "세부일정 수정", description = "세부일정 수정 API")
	public CustomApiResponse<Void> updateDetail(
		@PathVariable Long scheduleId,
		@PathVariable Long detailId,
		@RequestBody UpdateDetailRequest request) {
		scheduleDetailService.updateDetail(scheduleId, detailId, request);
		return CustomApiResponse.success(null,200,"세부 일정 수정 성공");
	}

	@DeleteMapping("/{detailId}")
	@Operation(summary = "세부일정 삭제", description = "세부일정 삭제 API")
	public CustomApiResponse<Void> deleteDetail(
		@PathVariable Long scheduleId,
		@PathVariable Long detailId) {
		scheduleDetailService.deleteDetail(scheduleId, detailId);
		return CustomApiResponse.success(null,200,"세부 일정 삭제 성공");
	}
}

