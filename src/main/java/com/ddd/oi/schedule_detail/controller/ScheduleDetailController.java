package com.ddd.oi.schedule_detail.controller;

import com.ddd.oi.schedule_detail.dto.response.CreateScheduleDetailResponse;
import com.ddd.oi.schedule_detail.dto.response.UpdateScheduleDetailResponse;
import com.ddd.oi.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddd.oi.common.response.CustomApiResponse;
import com.ddd.oi.schedule_detail.dto.request.CreateDetailRequest;
import com.ddd.oi.schedule_detail.dto.request.UpdateDetailRequest;
import com.ddd.oi.schedule_detail.dto.response.ScheduleDetailGroupedResponse;
import com.ddd.oi.schedule_detail.service.ScheduleDetailService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
	public CustomApiResponse<List<ScheduleDetailGroupedResponse>> getDetails(
			@AuthenticationPrincipal User user,
			@PathVariable("scheduleId") Long scheduleId) {
		List<ScheduleDetailGroupedResponse> result = scheduleDetailService.getGroupedDetails(user,scheduleId);
		return CustomApiResponse.success(result, 200, "세부 일정 목록 조회 성공");
	}

	@PostMapping
	@Operation(
		summary = "세부일정 생성",
		description = "세부일정 생성 API",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = @Content(
				mediaType = "application/json",
				examples = @ExampleObject(
					name = "CreateDetailRequest Example",
					value = "[{\"targetDate\": \"2025-07-27\", \"memo\": \"Meeting at Starbucks\", \"spotName\": \"스타벅스 한국프레스센터점\", \"latitude\": 37.5674232, \"longitude\": 126.9778908, \"category\": \"카페>디저트\"},"
						+ "{\"targetDate\": \"2025-07-28\", \"memo\": \"Dinner at Italian Restaurant\", \"spotName\": \"이탈리안 레스토랑\", \"latitude\": 37.5651234, \"longitude\": 126.9785678, \"category\": \"음식점>양식\"}]"
				)
			)
		),
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "세부 일정 생성 성공",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = CreateScheduleDetailResponse.class)
				)
			)
		}
	)	public CustomApiResponse<List<CreateScheduleDetailResponse>> createDetails(
			@AuthenticationPrincipal User user,
			@PathVariable("scheduleId") Long scheduleId,
			@RequestBody List<CreateDetailRequest> requests) {
		List<CreateScheduleDetailResponse> result = scheduleDetailService.createDetails(user,scheduleId, requests);
		return CustomApiResponse.success(result, 200, "세부 일정 생성 성공");
	}

	@PutMapping("/{detailId}")
	@Operation(
		summary = "세부일정 수정",
		description = "세부일정 수정 API",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = @Content(
				mediaType = "application/json",
				examples = @ExampleObject(
					name = "UpdateDetailRequest Example",
					value = "{\"startTime\": \"14:30\", \"targetDate\": \"2025-07-27\", \"memo\": \"Visit Starbucks\", \"spotName\": \"스타벅스 한국프레스센터점\", \"latitude\": 37.5674232, \"longitude\": 126.9778908}"
				)
			)
		),
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "세부 일정 수정 성공",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = UpdateScheduleDetailResponse.class)
				)
			)
		}
	)	public CustomApiResponse<UpdateScheduleDetailResponse> updateDetail(
			@AuthenticationPrincipal User user,
			@PathVariable("scheduleId") Long scheduleId,
			@PathVariable("detailId") Long detailId,
			@RequestBody UpdateDetailRequest request) {
		UpdateScheduleDetailResponse result = scheduleDetailService.updateDetail(user,scheduleId, detailId, request);
		return CustomApiResponse.success(result, 200, "세부 일정 수정 성공");
	}

	@DeleteMapping("/{detailId}")
	@Operation(summary = "세부일정 삭제", description = "세부일정 삭제 API")
	public CustomApiResponse<Void> deleteDetail(
			@AuthenticationPrincipal User user,
			@PathVariable("scheduleId") Long scheduleId,
			@PathVariable("detailId") Long detailId) {
		scheduleDetailService.deleteDetail(user,scheduleId, detailId);
		return CustomApiResponse.success(null, 200, "세부 일정 삭제 성공");
	}
}
