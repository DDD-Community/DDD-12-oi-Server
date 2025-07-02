package com.ddd.oi.schedule_detail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.ddd.oi.schedule_detail.domain.ScheduleDetail;

@Repository
public interface ScheduleDetailRepository extends JpaRepository<ScheduleDetail, Long> {
	@Query("SELECT sd FROM ScheduleDetail sd WHERE sd.schedule.id = :scheduleId ORDER BY sd.targetDate, sd.startTime")
	List<ScheduleDetail> findByScheduleId(@Param("scheduleId") Long scheduleId);
	Optional<ScheduleDetail> findByIdAndSchedule_Id(Long scheduleDetailId, Long scheduleId);
}
