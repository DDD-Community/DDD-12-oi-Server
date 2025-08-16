package com.ddd.oi.schedule_detail.controller.docs;

import com.ddd.oi.common.response.CustomApiResponse;
import com.ddd.oi.schedule_detail.dto.request.CreateDetailRequest;
import com.ddd.oi.schedule_detail.dto.request.UpdateDetailRequest;
import com.ddd.oi.schedule_detail.dto.response.CreateScheduleDetailResponse;
import com.ddd.oi.schedule_detail.dto.response.ScheduleDetailGroupedResponse;
import com.ddd.oi.schedule_detail.dto.response.UpdateScheduleDetailResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "스케줄 상세 컨트롤러", description = "스케줄 상세 관련 API입니다.")
public interface ScheduleDetailControllerDocs {

    @Operation(summary = "세부일정 목록 조회", description = "세부일정 목록 조회 API")
    CustomApiResponse<List<ScheduleDetailGroupedResponse>> getDetails(
        @PathVariable("scheduleId") Long scheduleId);

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
        responses = @ApiResponse(
            responseCode = "200",
            description = "세부 일정 생성 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CreateScheduleDetailResponse.class)
            )
        )
    )
    CustomApiResponse<List<CreateScheduleDetailResponse>> createDetails(
        @PathVariable("scheduleId") Long scheduleId,
        @RequestBody List<CreateDetailRequest> requests);

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
        responses = @ApiResponse(
            responseCode = "200",
            description = "세부 일정 수정 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UpdateScheduleDetailResponse.class)
            )
        )
    )
    CustomApiResponse<UpdateScheduleDetailResponse> updateDetail(
        @PathVariable("scheduleId") Long scheduleId,
        @PathVariable("detailId") Long detailId,
        @RequestBody UpdateDetailRequest request);

    @Operation(summary = "세부일정 삭제", description = "세부일정 삭제 API")
    CustomApiResponse<Void> deleteDetail(
        @PathVariable("scheduleId") Long scheduleId,
        @PathVariable("detailId") Long detailId);
}
