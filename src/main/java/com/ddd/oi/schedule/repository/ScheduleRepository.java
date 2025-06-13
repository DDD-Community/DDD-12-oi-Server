package com.ddd.oi.schedule.repository;

import com.ddd.oi.schedule.domain.Schedule;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findByUser_UserIdAndScheduleId(Long userId, Long scheduleId);
    @Query("SELECT s FROM Schedule s WHERE s.user.userId = :userId AND s.startDate <= :targetDay AND s.endDate >= :targetDay")
    List<Schedule> findSchedulesByUserIdAndTargetDay(@Param("userId") Long userId, @Param("targetDay") LocalDate targetDay);
    @Query("SELECT s FROM Schedule s WHERE s.user.userId = :userId AND " +
            "((YEAR(s.startDate) = :year AND MONTH(s.startDate) = :month) OR " +
            "(YEAR(s.endDate) = :year AND MONTH(s.endDate) = :month) OR " +
            "(s.startDate <= :endOfMonth AND s.endDate >= :startOfMonth))")
    List<Schedule> findSchedulesByUserIdAndMonth(
            @Param("userId") Long userId,
            @Param("year") int year,
            @Param("month") int month,
            @Param("startOfMonth") LocalDate startOfMonth,
            @Param("endOfMonth") LocalDate endOfMonth
    );

}
