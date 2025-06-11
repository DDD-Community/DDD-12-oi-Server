package com.ddd.oi.schedule_detail.service;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.schedule.domain.Schedule;
import com.ddd.oi.schedule.repository.ScheduleRepository;
import com.ddd.oi.schedule_detail.domain.ScheduleDetail;
import com.ddd.oi.schedule_detail.dto.request.CreateDetailRequest;
import com.ddd.oi.schedule_detail.dto.request.UpdateDetailRequest;
import com.ddd.oi.schedule_detail.dto.response.*;
import com.ddd.oi.schedule_detail.repository.ScheduleDetailRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleDetailService {

	private final ScheduleDetailRepository scheduleDetailRepository;
	private final ScheduleRepository scheduleRepository;

	@Transactional(readOnly = true)
	public List<ScheduleDetailResponse> getDetails(Long scheduleId, LocalDate targetDate) {
		Schedule schedule = scheduleRepository.findById(scheduleId)
			.orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));

		return scheduleDetailRepository.findBySchedule_ScheduleIdAndTargetDate(scheduleId, targetDate)
			.stream()
			.map(ScheduleDetailResponse::from)
			.toList();
	}

	@Transactional
	public void createDetail(Long scheduleId, CreateDetailRequest request) {
		Schedule schedule = scheduleRepository.findById(scheduleId)
			.orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));

		ScheduleDetail detail = ScheduleDetail.builder()
			.schedule(schedule)
			.startTime(request.startTime())
			.targetDate(request.targetDate())
			.memo(request.memo())
			.spotName(request.spotName())
			.latitude(request.latitude())
			.longitude(request.longitude())
			.build();

		ScheduleDetail saved = scheduleDetailRepository.save(detail);
	}

	@Transactional
	public void updateDetail(Long scheduleId, Long detailId, UpdateDetailRequest request) {
		Schedule schedule = scheduleRepository.findById(scheduleId)
			.orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));

		ScheduleDetail detail = scheduleDetailRepository.findByScheduleDetailIdAndSchedule_ScheduleId(detailId, scheduleId)
			.orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));

		detail.update(
			request.startTime(),
			request.memo(),
			request.spotName(),
			request.latitude(),
			request.longitude()
		);
	}

	@Transactional
	public void deleteDetail(Long scheduleId, Long detailId) {
		ScheduleDetail detail = scheduleDetailRepository.findByScheduleDetailIdAndSchedule_ScheduleId(detailId, scheduleId)
			.orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));

		scheduleDetailRepository.delete(detail);
	}
}
