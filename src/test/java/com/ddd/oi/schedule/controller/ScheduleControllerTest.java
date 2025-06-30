package com.ddd.oi.schedule.controller;

import com.ddd.oi.schedule.domain.enumType.GroupTag;
import com.ddd.oi.schedule.domain.enumType.Mobility;
import com.ddd.oi.schedule.domain.enumType.ScheduleTag;
import com.ddd.oi.schedule.dto.request.CreateScheduleRequest;
import com.ddd.oi.schedule.dto.request.UpdateScheduleRequest;
import com.ddd.oi.schedule.dto.response.CreateScheduleResponse;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        CreateScheduleResponse response = new CreateScheduleResponse(
                1L,
                "test_schedule",
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 2),
                Mobility.CAR,
                List.of("FRIEND"),
                ScheduleTag.BUSINESS
        );

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

        UpdateScheduleResponse response = new UpdateScheduleResponse(
                1L,
                "test_schedule",
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 2),
                Mobility.CAR,
                List.of("FRIEND"),
                ScheduleTag.BUSINESS
        );

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
}
