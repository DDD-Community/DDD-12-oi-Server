package com.ddd.oi.schedule_detail.controller;

import com.ddd.oi.common.response.CustomApiResponse;
import com.ddd.oi.schedule_detail.dto.request.CreateDetailRequest;
import com.ddd.oi.schedule_detail.dto.request.UpdateDetailRequest;
import com.ddd.oi.schedule_detail.dto.response.CreateScheduleDetailResponse;
import com.ddd.oi.schedule_detail.dto.response.ScheduleDetailGroupedResponse;
import com.ddd.oi.schedule_detail.dto.response.ScheduleDetailResponse;
import com.ddd.oi.schedule_detail.dto.response.UpdateScheduleDetailResponse;
import com.ddd.oi.schedule_detail.service.ScheduleDetailService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ScheduleDetailControllerTest {

	private final ScheduleDetailService scheduleDetailService = mock(ScheduleDetailService.class);
	private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new ScheduleDetailController(scheduleDetailService)).build();

	@Test
	@DisplayName("세부일정 목록 조회 성공")
	void 스케줄_세부일정_조회_성공() throws Exception {
		// Given
		when(scheduleDetailService.getGroupedDetails(1L)).thenReturn(List.of(
			ScheduleDetailGroupedResponse.builder()
				.targetDate(LocalDate.of(2025, 7, 1))
				.details(List.of(
					ScheduleDetailResponse.builder()
						.id(1L)
						.startTime(LocalTime.of(10, 0))
						.targetDate(LocalDate.of(2025, 7, 1))
						.spotName("spot1")
						.latitude(10.0)
						.longitude(20.0)
						.memo("memo1")
						.build(),
					ScheduleDetailResponse.builder()
						.id(2L)
						.startTime(LocalTime.of(11, 0))
						.targetDate(LocalDate.of(2025, 7, 1))
						.spotName("spot2")
						.latitude(15.0)
						.longitude(25.0)
						.memo("memo2")
						.build()
				))
				.build(),
			ScheduleDetailGroupedResponse.builder()
				.targetDate(LocalDate.of(2025, 7, 2))
				.details(List.of(
					ScheduleDetailResponse.builder()
						.id(3L)
						.startTime(LocalTime.of(12, 0))
						.targetDate(LocalDate.of(2025, 7, 2))
						.spotName("spot3")
						.latitude(20.0)
						.longitude(30.0)
						.memo("memo3")
						.build(),
					ScheduleDetailResponse.builder()
						.id(4L)
						.startTime(LocalTime.of(13, 0))
						.targetDate(LocalDate.of(2025, 7, 2))
						.spotName("spot4")
						.latitude(25.0)
						.longitude(35.0)
						.memo("memo4")
						.build()
				))
				.build()
		));

		// When & Then
		mockMvc.perform(get("/api/v1/schedules/1/details"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data[0].targetDate").value("2025-07-01"))
			.andExpect(jsonPath("$.data[0].details[0].memo").value("memo1"))
			.andExpect(jsonPath("$.data[0].details[1].spotName").value("spot2"))
			.andExpect(jsonPath("$.data[1].targetDate").value("2025-07-02"))
			.andExpect(jsonPath("$.data[1].details[0].latitude").value(20.0))
			.andExpect(jsonPath("$.data[1].details[1].longitude").value(35.0));
	}

	@Test
	@DisplayName("세부일정 생성 요청 성공")
	void 스케줄_상세_생성_요청_성공() throws Exception {
		// Given
		CreateScheduleDetailResponse mockResponse = CreateScheduleDetailResponse.builder()
				.scheduleDetailId(1L)
				.targetDate(LocalDate.of(2026, 11, 1))
				.memo("memo")
				.spotName("spot")
				.latitude(10.0)
				.longitude(20.0)
				.build();

		when(scheduleDetailService.createDetail(eq(1L), any()))
				.thenReturn(mockResponse);

		// When & Then
		mockMvc.perform(post("/api/v1/schedules/1/details")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
				{
				
					"targetDate": "2026-11-01",
					"memo": "memo",
					"spotName": "spot",
					"latitude": 10.0,
					"longitude": 20.0
				}
			"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.scheduleDetailId").value(1))
				.andExpect(jsonPath("$.data.targetDate").value("2026-11-01"))
				.andExpect(jsonPath("$.data.memo").value("memo"))
				.andExpect(jsonPath("$.data.spotName").value("spot"))
				.andExpect(jsonPath("$.data.latitude").value(10.0))
				.andExpect(jsonPath("$.data.longitude").value(20.0));
	}

	@Test
	@DisplayName("세부일정 수정 요청 성공")
	void 스케줄_상세_수정_요청_성공() throws Exception {
		// Given
		UpdateScheduleDetailResponse mockResponse = UpdateScheduleDetailResponse.builder()
				.scheduleDetailId(1L)
				.startTime(LocalTime.of(14, 30))
				.targetDate(LocalDate.of(2026, 11, 1))
				.memo("수정 메모")
				.spotName("수정 장소")
				.latitude(35.123)
				.longitude(128.456)
				.build();

		when(scheduleDetailService.updateDetail(eq(1L), eq(1L), any()))
				.thenReturn(mockResponse);

		// When & Then
		mockMvc.perform(put("/api/v1/schedules/1/details/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
				{
					"startTime": "14:30",
					"targetDate": "2026-11-01",
					"memo": "수정 메모",
					"spotName": "수정 장소",
					"latitude": 35.123,
					"longitude": 128.456
				}
			"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.scheduleDetailId").value(1))
				.andExpect(jsonPath("$.data.startTime").value("14:30"))
				.andExpect(jsonPath("$.data.targetDate").value("2026-11-01"))
				.andExpect(jsonPath("$.data.memo").value("수정 메모"))
				.andExpect(jsonPath("$.data.spotName").value("수정 장소"))
				.andExpect(jsonPath("$.data.latitude").value(35.123))
				.andExpect(jsonPath("$.data.longitude").value(128.456));
	}

	@Test
	@DisplayName("세부일정 삭제 요청 성공")
	void 스케줄_상세_삭제요청_성공() throws Exception {
		// Given
		doNothing().when(scheduleDetailService).deleteDetail(1L, 1L);

		// When & Then
		mockMvc.perform(delete("/api/v1/schedules/1/details/1"))
			.andExpect(status().isOk());
	}
}
