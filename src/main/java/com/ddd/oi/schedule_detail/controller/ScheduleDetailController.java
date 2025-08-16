package com.ddd.oi.schedule_detail.controller;

import com.ddd.oi.schedule_detail.controller.docs.ScheduleDetailControllerDocs;
import com.ddd.oi.schedule_detail.dto.request.CreateDetailRequest;
import com.ddd.oi.schedule_detail.dto.request.UpdateDetailRequest;
import com.ddd.oi.schedule_detail.dto.response.CreateScheduleDetailResponse;
import com.ddd.oi.schedule_detail.dto.response.ScheduleDetailGroupedResponse;
import com.ddd.oi.schedule_detail.dto.response.UpdateScheduleDetailResponse;
import com.ddd.oi.schedule_detail.service.ScheduleDetailService;
import com.ddd.oi.common.response.CustomApiResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules/{scheduleId}/details")
@RequiredArgsConstructor
public class ScheduleDetailController implements ScheduleDetailControllerDocs {

	private final ScheduleDetailService scheduleDetailService;

	@GetMapping
	@Override
	public CustomApiResponse<List<ScheduleDetailGroupedResponse>> getDetails(@PathVariable("scheduleId") Long scheduleId) {
		List<ScheduleDetailGroupedResponse> result = scheduleDetailService.getGroupedDetails(scheduleId);
		return CustomApiResponse.success(result, 200, "세부 일정 목록 조회 성공");
	}

	@PostMapping
	@Override
	public CustomApiResponse<List<CreateScheduleDetailResponse>> createDetails(
		@PathVariable("scheduleId") Long scheduleId,
		@RequestBody List<CreateDetailRequest> requests) {
		List<CreateScheduleDetailResponse> result = scheduleDetailService.createDetails(scheduleId, requests);
		return CustomApiResponse.success(result, 200, "세부 일정 생성 성공");
	}

	@PutMapping("/{detailId}")
	@Override
	public CustomApiResponse<UpdateScheduleDetailResponse> updateDetail(
		@PathVariable("scheduleId") Long scheduleId,
		@PathVariable("detailId") Long detailId,
		@RequestBody UpdateDetailRequest request) {
		UpdateScheduleDetailResponse result = scheduleDetailService.updateDetail(scheduleId, detailId, request);
		return CustomApiResponse.success(result, 200, "세부 일정 수정 성공");
	}

	@DeleteMapping("/{detailId}")
	@Override
	public CustomApiResponse<Void> deleteDetail(
		@PathVariable("scheduleId") Long scheduleId,
		@PathVariable("detailId") Long detailId) {
		scheduleDetailService.deleteDetail(scheduleId, detailId);
		return CustomApiResponse.success(null, 200, "세부 일정 삭제 성공");
	}
}
