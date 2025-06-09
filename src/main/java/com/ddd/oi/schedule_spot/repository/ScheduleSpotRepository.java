package com.ddd.oi.schedule_spot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ddd.oi.schedule_spot.domain.ScheduleSpot;

@Repository
public interface ScheduleSpotRepository extends JpaRepository<ScheduleSpot, Long> {
}
