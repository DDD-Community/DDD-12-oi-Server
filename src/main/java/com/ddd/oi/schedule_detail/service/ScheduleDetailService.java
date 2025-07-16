package com.ddd.oi.schedule_detail.service;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.common.config.CategoryMapping;
import com.ddd.oi.schedule.domain.Schedule;
import com.ddd.oi.schedule.repository.ScheduleRepository;
import com.ddd.oi.schedule_detail.domain.ScheduleDetail;
import com.ddd.oi.schedule_detail.dto.request.CreateDetailRequest;
import com.ddd.oi.schedule_detail.dto.request.UpdateDetailRequest;
import com.ddd.oi.schedule_detail.dto.response.CreateScheduleDetailResponse;
import com.ddd.oi.schedule_detail.dto.response.ScheduleDetailGroupedResponse;
import com.ddd.oi.schedule_detail.dto.response.UpdateScheduleDetailResponse;
import com.ddd.oi.schedule_detail.repository.ScheduleDetailRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleDetailService {

	private final ScheduleDetailRepository scheduleDetailRepository;
	private final ScheduleRepository scheduleRepository;
	private final CategoryMapping categoryMapping;

	private static final int MAX_CREATE_COUNT = 5;

	@Transactional(readOnly = true)
	public List<ScheduleDetailGroupedResponse> getGroupedDetails(Long scheduleId) {
		findExistingSchedule(scheduleId);

		return scheduleDetailRepository.findByScheduleId(scheduleId)
				.stream()
				.collect(Collectors.groupingBy(ScheduleDetail::getTargetDate))
				.entrySet()
				.stream()
				.map(entry -> ScheduleDetailGroupedResponse.from(entry.getKey(), entry.getValue()))
				.toList();
	}

	@Transactional
	public List<CreateScheduleDetailResponse> createDetails(Long scheduleId, List<CreateDetailRequest> requests) {
		if (requests.size() > MAX_CREATE_COUNT) {
			throw new OiException(ErrorCode.SCHEDULE_DETAIL_CREATE_LIMIT_EXCEEDED);
		}
		Schedule schedule = findExistingSchedule(scheduleId);
		List<CreateScheduleDetailResponse> responses = new java.util.ArrayList<>();
		for (CreateDetailRequest request : requests) {
			String mainCategory = categoryMapping.mapToMainCategory(request.category());
			ScheduleDetail detail = request.toEntity(schedule, mainCategory);
			scheduleDetailRepository.save(detail);
			responses.add(CreateScheduleDetailResponse.of(detail));
		}
		return responses;
	}

	@Transactional
	public UpdateScheduleDetailResponse updateDetail(Long scheduleId, Long detailId, UpdateDetailRequest request) {
		Schedule schedule = findExistingSchedule(scheduleId);
		ScheduleDetail detail = findExistingScheduleDetail(detailId, scheduleId);

		if (request.targetDate() != null &&
				(request.targetDate().isBefore(schedule.getStartDate()) ||
						request.targetDate().isAfter(schedule.getEndDate()))) {
			throw new OiException(ErrorCode.INVALID_TARGET_DATE);
		}
		String mappedCategory = categoryMapping.mapToMainCategory(request.spotName());

		detail.update(
				request.startTime() != null ? request.startTime() : detail.getStartTime(),
				request.targetDate(),
				request.memo(),
				request.spotName(),
				request.latitude(),
				request.longitude(),
				mappedCategory);
		return UpdateScheduleDetailResponse.of(detail);
	}

	@Transactional
	public void deleteDetail(Long scheduleId, Long detailId) {
		findExistingSchedule(scheduleId);
		ScheduleDetail detail = findExistingScheduleDetail(detailId, scheduleId);

		scheduleDetailRepository.delete(detail);
	}

	private Schedule findExistingSchedule(Long scheduleId) {
		return scheduleRepository.findById(scheduleId)
				.orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));
	}

	private ScheduleDetail findExistingScheduleDetail(Long detailId, Long scheduleId) {
		return scheduleDetailRepository.findByIdAndSchedule_Id(detailId, scheduleId)
				.orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));
	}
}
