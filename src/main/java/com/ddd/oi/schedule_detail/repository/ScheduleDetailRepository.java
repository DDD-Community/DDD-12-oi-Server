package com.ddd.oi.schedule_detail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.ddd.oi.schedule_detail.domain.ScheduleDetail;

@Repository
public interface ScheduleDetailRepository extends JpaRepository<ScheduleDetail, Long> {
	List<ScheduleDetail> findBySchedule_IdAndTargetDate(Long scheduleId, LocalDate targetDate);

	Optional<ScheduleDetail> findByIdAndSchedule_Id(Long scheduleDetailId, Long scheduleId);
}
