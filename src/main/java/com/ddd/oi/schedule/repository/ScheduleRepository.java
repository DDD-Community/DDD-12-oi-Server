package com.ddd.oi.schedule.repository;

import com.ddd.oi.schedule.domain.Schedule;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
        Optional<Schedule> findByUser_IdAndId(Long userId, Long scheduleId);

        @Query("SELECT s FROM Schedule s " +
            "WHERE s.user.id = :userId " +
            "AND :targetDay BETWEEN s.startDate AND s.endDate " +
            "ORDER BY s.startDate")
        List<Schedule> findSchedulesByUserIdAndTargetDay(
            @Param("userId") Long userId,
            @Param("targetDay") LocalDate targetDay);

        @Query("SELECT s FROM Schedule s " +
            "WHERE s.user.id = :userId " +
            "AND ((YEAR(s.startDate) = :year AND MONTH(s.startDate) = :month) " +
            "   OR (YEAR(s.endDate) = :year AND MONTH(s.endDate) = :month) " +
            "   OR (s.startDate <= :endOfMonth AND s.endDate >= :startOfMonth)) " +
            "ORDER BY s.startDate")
        List<Schedule> findSchedulesByUserIdAndMonth(
            @Param("userId") Long userId,
            @Param("year") int year,
            @Param("month") int month,
            @Param("startOfMonth") LocalDate startOfMonth,
            @Param("endOfMonth") LocalDate endOfMonth);


        @Query("SELECT COUNT(s) FROM Schedule s WHERE s.user.id = :userId AND :date BETWEEN s.startDate AND s.endDate")
        int countByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

        @Query("SELECT s FROM Schedule s JOIN FETCH s.groups WHERE s.user.id = :userId AND s.createdAt BETWEEN :startDate AND :endDate")
        List<Schedule> findByUser_IdAndCreatedAtBetween(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
        );}
