package com.ddd.oi.schedule.controller;

import com.ddd.oi.common.response.CustomApiResponse;
import com.ddd.oi.schedule.dto.request.CreateScheduleRequest;
import com.ddd.oi.schedule.dto.response.CreateScheduleResponse;
import com.ddd.oi.schedule.service.ScheduleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
@Tag(name = "스케줄 컨트롤러", description = "스케줄 관련 API입니다.")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public CustomApiResponse<CreateScheduleResponse> createSchedule(
           @RequestHeader("user-no") Long userId, @RequestBody CreateScheduleRequest request
    ) {
        CreateScheduleResponse result = scheduleService.createSchedule(userId, request);
        return CustomApiResponse.success(result, 200, "스케줄 생성 성공");
    }

    @DeleteMapping("/{scheduleId}")
    public CustomApiResponse<Void> deleteSchedule(
            @RequestHeader("user-no") Long userId,@PathVariable("scheduleId") Long scheduleId
    ) {
        scheduleService.deleteSchedule(userId,scheduleId);
        return CustomApiResponse.success(null,200,"일정 삭제 성공");
    }
}
