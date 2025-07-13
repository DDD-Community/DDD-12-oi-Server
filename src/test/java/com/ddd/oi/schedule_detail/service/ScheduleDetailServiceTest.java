package com.ddd.oi.schedule_detail.service;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.schedule.domain.Schedule;
import com.ddd.oi.schedule.repository.ScheduleRepository;
import com.ddd.oi.schedule_detail.domain.ScheduleDetail;
import com.ddd.oi.schedule_detail.dto.request.CreateDetailRequest;
import com.ddd.oi.schedule_detail.dto.request.UpdateDetailRequest;
import com.ddd.oi.schedule_detail.repository.ScheduleDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleDetailServiceTest {

    @InjectMocks
    private ScheduleDetailService service;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private ScheduleDetailRepository scheduleDetailRepository;

    private Schedule schedule;

    @BeforeEach
    void setup() {
        schedule = Schedule.builder()
                .id(1L)
                .scheduleTitle("테스트 일정")
                .startDate(LocalDate.of(2026, 5, 1))
                .endDate(LocalDate.of(2026, 5, 10))
                .build();
    }

    @Test
    @DisplayName("세부 일정 생성에 성공한다")
    void 세부_일정_생성_성공() {
        CreateDetailRequest request = new CreateDetailRequest(
                LocalDate.of(2026, 5, 3),
                "메모입니다",
                "강남역",
                37.5,
                127.0
        );
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        service.createDetail(1L, request);
        verify(scheduleDetailRepository).save(any(ScheduleDetail.class));
    }

    @Test
    @DisplayName("세부 일정 생성 시 날짜가 범위를 벗어나면 예외가 발생한다")
    void 날짜_범위_벗어나면_예외_발생() {
        CreateDetailRequest request = new CreateDetailRequest(
                LocalDate.of(2026, 5, 20),
                "메모입니다",
                "강남역",
                37.5,
                127.0
        );
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        OiException exception = assertThrows(OiException.class, () ->
                service.createDetail(1L, request));
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.INVALID_TARGET_DATE);
    }

    @Test
    @DisplayName("세부 일정 수정에 성공한다")
    void 세부_일정_수정_성공() {
        ScheduleDetail detail = mock(ScheduleDetail.class);
        UpdateDetailRequest request = new UpdateDetailRequest(
                LocalTime.of(13, 0),
                LocalDate.of(2026, 5, 5),
                "업데이트 메모",
                "서울역",
                36.5,
                126.5
        );
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        when(scheduleDetailRepository.findByIdAndSchedule_Id(2L, 1L)).thenReturn(Optional.of(detail));
        service.updateDetail(1L, 2L, request);
        verify(detail).update(
                eq(request.startTime()),
                eq(request.memo()),
                eq(request.spotName()),
                eq(request.latitude()),
                eq(request.longitude())
        );
    }

    @Test
    @DisplayName("세부 일정 삭제에 성공한다")
    void 세부_일정_삭제_성공() {
        ScheduleDetail detail = mock(ScheduleDetail.class);
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        when(scheduleDetailRepository.findByIdAndSchedule_Id(2L, 1L)).thenReturn(Optional.of(detail));
        service.deleteDetail(1L, 2L);
        verify(scheduleDetailRepository).delete(detail);
    }

    @Test
    @DisplayName("스케줄 또는 상세 ID가 존재하지 않으면 ENTITY_NOT_FOUND 예외가 발생한다")
    void 공통_엔티티_없을때_예외_발생() {
        when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());
        OiException ex1 = assertThrows(OiException.class, () ->
                service.deleteDetail(1L, 2L));
        assertThat(ex1.getErrorCode()).isEqualTo(ErrorCode.ENTITY_NOT_FOUND);

        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        when(scheduleDetailRepository.findByIdAndSchedule_Id(2L, 1L)).thenReturn(Optional.empty());
        OiException ex2 = assertThrows(OiException.class, () ->
                service.deleteDetail(1L, 2L));
        assertThat(ex2.getErrorCode()).isEqualTo(ErrorCode.ENTITY_NOT_FOUND);
    }
}
