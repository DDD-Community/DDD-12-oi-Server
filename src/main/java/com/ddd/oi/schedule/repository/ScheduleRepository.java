package com.ddd.oi.schedule.repository;

import com.ddd.oi.schedule.domain.Schedule;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findByUser_UserIdAndScheduleId(Long userId, Long scheduleId);

}
