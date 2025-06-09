package com.ddd.oi.schedule_detail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ddd.oi.schedule_detail.domain.ScheduleDetail;

@Repository
public interface ScheduleDetailRepository extends JpaRepository<ScheduleDetail, Long> {

}
