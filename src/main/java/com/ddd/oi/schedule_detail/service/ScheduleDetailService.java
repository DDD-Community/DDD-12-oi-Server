package com.ddd.oi.schedule_detail.service;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.schedule.domain.Schedule;
import com.ddd.oi.schedule.repository.ScheduleRepository;
import com.ddd.oi.schedule_detail.domain.ScheduleDetail;
import com.ddd.oi.schedule_detail.dto.request.CreateDetailRequest;
import com.ddd.oi.schedule_detail.dto.request.UpdateDetailRequest;
import com.ddd.oi.schedule_detail.dto.response.ScheduleDetailGroupedResponse;
import com.ddd.oi.schedule_detail.dto.response.ScheduleDetailResponse;
import com.ddd.oi.schedule_detail.repository.ScheduleDetailRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleDetailService {

	private final ScheduleDetailRepository scheduleDetailRepository;
	private final ScheduleRepository scheduleRepository;

	@Transactional(readOnly = true)
	public List<ScheduleDetailGroupedResponse> getGroupedDetails(Long scheduleId) {
		validateScheduleById(scheduleId);

		return scheduleDetailRepository.findByScheduleId(scheduleId)
			.stream()
			.collect(Collectors.groupingBy(ScheduleDetail::getTargetDate))
			.entrySet()
			.stream()
			.map(entry -> ScheduleDetailGroupedResponse.from(entry.getKey(), entry.getValue()))
			.toList();
	}

	@Transactional
	public void createDetail(Long scheduleId, CreateDetailRequest request) {
		Schedule schedule = validateScheduleById(scheduleId);

		if (request.targetDate().isBefore(schedule.getStartDate()) || request.targetDate()
			.isAfter(schedule.getEndDate())) {
			throw new OiException(ErrorCode.INVALID_TARGET_DATE);
		}
		ScheduleDetail detail = request.toEntity();
		scheduleDetailRepository.save(detail);
	}

	@Transactional
	public void updateDetail(Long scheduleId, Long detailId, UpdateDetailRequest request) {
		validateScheduleById(scheduleId);
		ScheduleDetail detail = validateScheduleDetailByIdAndScheduleId(detailId, scheduleId);

		detail.update(
			request.startTime(),
			request.memo(),
			request.spotName(),
			request.latitude(),
			request.longitude());
	}

	@Transactional
	public void deleteDetail(Long scheduleId, Long detailId) {
		validateScheduleById(scheduleId);
		ScheduleDetail detail = validateScheduleDetailByIdAndScheduleId(detailId, scheduleId);

		scheduleDetailRepository.delete(detail);
	}

	private Schedule validateScheduleById(Long scheduleId) {
		return scheduleRepository.findById(scheduleId)
			.orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));
	}

	private ScheduleDetail validateScheduleDetailByIdAndScheduleId(Long detailId, Long scheduleId) {
		return scheduleDetailRepository.findByIdAndSchedule_Id(detailId, scheduleId)
			.orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));
	}
}
