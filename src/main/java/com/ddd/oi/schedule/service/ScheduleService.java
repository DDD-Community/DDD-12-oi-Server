package com.ddd.oi.schedule.service;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.schedule.domain.Schedule;
import com.ddd.oi.schedule.dto.request.CreateScheduleRequest;
import com.ddd.oi.schedule.dto.request.UpdateScheduleRequest;
import com.ddd.oi.schedule.dto.response.CreateScheduleResponse;
import com.ddd.oi.schedule.dto.response.ScheduleListResponse;
import com.ddd.oi.schedule.dto.response.UpdateScheduleResponse;
import com.ddd.oi.schedule.repository.ScheduleRepository;
import com.ddd.oi.user.domain.User;
import com.ddd.oi.user.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

        private final ScheduleRepository scheduleRepository;
        private final UserRepository userRepository;

        @Transactional
        public CreateScheduleResponse createSchedule(User user, CreateScheduleRequest request) {
                User persistentUser = userRepository.findById(user.getId())
                        .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));

                LocalDate startDate = request.startDate();
                LocalDate endDate = request.endDate();

                boolean hasExceeded = startDate.datesUntil(endDate.plusDays(1))
                        .anyMatch(date -> scheduleRepository.countByUserIdAndDate(persistentUser.getId(), date) >= 3);

                if (hasExceeded) {
                        throw new OiException(ErrorCode.SCHEDULE_LIMIT_EXCEEDED);
                }
                Schedule newSchedule = request.toEntity(persistentUser);
                scheduleRepository.save(newSchedule);

                return CreateScheduleResponse.of(newSchedule);
        }


        @Transactional
        public Boolean deleteSchedule(User user, Long scheduleId) {
                User persistentUser = userRepository.findById(user.getId())
                                .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));

                Schedule schedule = scheduleRepository.findByUser_IdAndId(user.getId(),
                                scheduleId)
                                .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));

                scheduleRepository.delete(schedule);
                return true;
        }

        @Transactional
        public UpdateScheduleResponse updateSchedule(User user, Long scheduleId, UpdateScheduleRequest request) {
                User persistentUser = userRepository.findById(user.getId())
                                .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));

                Schedule schedule = scheduleRepository.findByUser_IdAndId(persistentUser.getId(),
                                scheduleId)
                                .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));

                schedule.updateSchedule(
                                request.title(),
                                request.startDate(),
                                request.endDate(),
                                request.mobility(),
                                request.scheduleTag(),
                                request.toGroupsEnum());

                return UpdateScheduleResponse.of(schedule);
        }

        @Transactional(readOnly = true)
        public List<ScheduleListResponse> showTargetDaySchedule(User user, LocalDate targetDay) {
                User persistentUser = userRepository.findById(user.getId())
                                .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));

                List<Schedule> schedules = scheduleRepository.findSchedulesByUserIdAndTargetDay(persistentUser.getId(),
                                targetDay);
                return schedules.stream()
                                .map(ScheduleListResponse::of)
                                .toList();
        }

        @Transactional(readOnly = true)
        public List<ScheduleListResponse> showMonthScheduleList(User user, int year, int month) {
                User persistentUser = userRepository.findById(user.getId())
                                .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));

                LocalDate startOfMonth = LocalDate.of(year, month, 1);
                LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

                List<Schedule> schedules = scheduleRepository.findSchedulesByUserIdAndMonth(
                        persistentUser.getId(), year, month, startOfMonth, endOfMonth);

                return schedules.stream()
                                .map(ScheduleListResponse::of)
                                .toList();
        }

}
