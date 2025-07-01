package com.ddd.oi.schedule.controller;

import com.ddd.oi.schedule.domain.enumType.GroupTag;
import com.ddd.oi.schedule.domain.enumType.Mobility;
import com.ddd.oi.schedule.domain.enumType.ScheduleTag;
import com.ddd.oi.schedule.dto.request.CreateScheduleRequest;
import com.ddd.oi.schedule.dto.request.UpdateScheduleRequest;
import com.ddd.oi.schedule.dto.response.CreateScheduleResponse;
import com.ddd.oi.schedule.dto.response.ScheduleListResponse;
import com.ddd.oi.schedule.dto.response.UpdateScheduleResponse;
import com.ddd.oi.schedule.service.ScheduleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ScheduleController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduleService scheduleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("스케줄 생성 요청 성공한다.")
    void 스케줄_생성_요청_성공() throws Exception {
        // Given
        CreateScheduleRequest request = new CreateScheduleRequest(
                "test_schedule",
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 2),
                Mobility.CAR,
                ScheduleTag.BUSINESS,
                List.of(GroupTag.FRIEND)
        );

        CreateScheduleResponse response = CreateScheduleResponse.builder()
                .scheduleId(1L)
                .title("test_schedule")
                .startDate(LocalDate.of(2025, 6, 1))
                .endDate(LocalDate.of(2025, 6, 2))
                .mobility(Mobility.CAR)
                .groups(List.of("FRIEND"))
                .scheduleTag(ScheduleTag.BUSINESS)
                .build();

        when(scheduleService.createSchedule(any(), any())).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/v1/schedules")
                        .header("user-no", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("스케줄 생성 성공"))
                .andExpect(jsonPath("$.data.scheduleId").value(1L));
    }

    @Test
    @DisplayName("스케줄 수정요청에 성공한다.")
    void 스케줄_수정_요청_성공() throws Exception {
        // Given
        UpdateScheduleRequest request = new UpdateScheduleRequest(
                "test_schedule",
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 2),
                Mobility.CAR,
                ScheduleTag.BUSINESS,
                List.of(GroupTag.FRIEND.name())
        );

        UpdateScheduleResponse response = UpdateScheduleResponse.builder()
                .scheduleId(1L)
                .title("test_schedule")
                .startDate(LocalDate.of(2025, 6, 1))
                .endDate(LocalDate.of(2025, 6, 2))
                .mobility(Mobility.CAR)
                .groups(List.of("FRIEND"))
                .scheduleTag(ScheduleTag.BUSINESS)
                .build();

        when(scheduleService.updateSchedule(any(), any(), any())).thenReturn(response);
        // When & Then
        mockMvc.perform(put("/api/v1/schedules/{scheduleId}",1L)
                        .header("user-no", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("스케줄 수정 성공"))
                .andExpect(jsonPath("$.data.scheduleId").value(1L));

    }
    @Test
    @DisplayName("스케줄 삭제요청에 성공한다.")
    void 스케줄_삭제요청_성공() throws Exception{
        // Given
        when(scheduleService.deleteSchedule(any(), any())).thenReturn(true);

        // When & Then
        mockMvc.perform(delete("/api/v1/schedules/{scheduleId}", 1L)
                        .header("user-no", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("일정 삭제 성공"));
    }
    @Test
    @DisplayName("스케줄 한달조회 요청에 성공한다.")
    void 스케줄_한달조회_요청_성공() throws Exception {
        // Given
        int year = 2025;
        int month = 5;

        ScheduleListResponse response1 = ScheduleListResponse.builder()
                .scheduleId(1L)
                .title("test_schedule1")
                .startDate(LocalDate.of(2025, 5, 1))
                .endDate(LocalDate.of(2025, 5, 5))
                .scheduleTag(ScheduleTag.BUSINESS)
                .mobility(Mobility.CAR)
                .groups(List.of(GroupTag.COUPLE))
                .build();

        ScheduleListResponse response2 = ScheduleListResponse.builder()
                .scheduleId(2L)
                .title("test_schedule2")
                .startDate(LocalDate.of(2025, 5, 10))
                .endDate(LocalDate.of(2025, 5, 12))
                .scheduleTag(ScheduleTag.DATE)
                .mobility(Mobility.CAR)
                .groups(List.of(GroupTag.CHILDREN,GroupTag.FRIEND))
                .build();

        when(scheduleService.showMonthScheduleList(1L, year, month))
                .thenReturn(List.of(response1, response2));

        // When & Then
        mockMvc.perform(get("/api/v1/schedules/{year}/{month}",year,month)
                        .header("user-no", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("해당 월의 일정들 조회 성공"))
                .andExpect(jsonPath("$.data[0].scheduleId").value(1L))
                .andExpect(jsonPath("$.data[1].scheduleId").value(2L));
    }
    @Test
    @DisplayName("스케줄 특정날짜 조회에 성공한다.")
    void 스케줄_특정날짜_조회성공() throws Exception {
        // Given
        LocalDate targetDay = LocalDate.of(2025,5,6);
        ScheduleListResponse response1 = ScheduleListResponse.builder()
                .scheduleId(1L)
                .title("test_schedule1")
                .startDate(LocalDate.of(2025, 5, 1))
                .endDate(LocalDate.of(2025, 5, 5))
                .scheduleTag(ScheduleTag.BUSINESS)
                .mobility(Mobility.CAR)
                .groups(List.of(GroupTag.COUPLE))
                .build();

        ScheduleListResponse response2 = ScheduleListResponse.builder()
                .scheduleId(2L)
                .title("test_schedule2")
                .startDate(LocalDate.of(2025, 5, 4))
                .endDate(LocalDate.of(2025, 5, 12))
                .scheduleTag(ScheduleTag.DATE)
                .mobility(Mobility.CAR)
                .groups(List.of(GroupTag.CHILDREN,GroupTag.FRIEND))
                .build();
        when(scheduleService.showTargetDaySchedule(1L, targetDay))
                .thenReturn(List.of(response1,response2));

        // When & Then
        mockMvc.perform(get("/api/v1/schedules/{target-day}",targetDay)
                        .header("user-no", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("해당 날짜의 일정들 조회 성공"))
                .andExpect(jsonPath("$.data[0].scheduleId").value(1L))
                .andExpect(jsonPath("$.data[1].scheduleId").value(2L));


    }

}
