package com.ddd.oi.schedule_detail.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.ddd.oi.schedule_detail.domain.ScheduleDetail;

public interface ScheduleDetailRepository extends JpaRepository<ScheduleDetail, Long> {
	List<ScheduleDetail> findBySchedule_ScheduleIdAndTargetDate(Long scheduleId, LocalDate targetDate);
	Optional<ScheduleDetail> findByScheduleDetailIdAndSchedule_ScheduleId(Long detailId, Long scheduleId);
}

