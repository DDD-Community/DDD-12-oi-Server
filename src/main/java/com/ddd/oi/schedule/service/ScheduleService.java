package com.ddd.oi.schedule.service;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.schedule.domain.Schedule;
import com.ddd.oi.schedule.dto.request.CreateScheduleRequest;
import com.ddd.oi.schedule.dto.request.UpdateScheduleRequest;
import com.ddd.oi.schedule.dto.response.CreateScheduleResponse;
import com.ddd.oi.schedule.dto.response.UpdateScheduleResponse;
import com.ddd.oi.schedule.repository.ScheduleRepository;
import com.ddd.oi.user.domain.User;
import com.ddd.oi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

     @Transactional
    public CreateScheduleResponse createSchedule(Long userId, CreateScheduleRequest request) {
         User user = userRepository.findById(userId)
                 .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));

         Schedule newSchedule = request.toEntity(user);

         Schedule savedSchedule = scheduleRepository.save(newSchedule);

         return new CreateScheduleResponse(savedSchedule.getScheduleId());
     }

     @Transactional
    public void deleteSchedule(Long userId, Long scheduleId) {
         User user = userRepository.findById(userId)
                 .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));

        Schedule schedule = scheduleRepository.findByUser_UserIdAndScheduleId(userId,scheduleId)
                .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));

        scheduleRepository.delete(schedule);

     }

    @Transactional
    public UpdateScheduleResponse updateSchedule(Long userId, Long scheduleId, UpdateScheduleRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));

        Schedule schedule = scheduleRepository.findByUser_UserIdAndScheduleId(userId, scheduleId)
                .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));

        schedule.updateSchedule(
                request.title(),
                request.startDate(),
                request.endDate(),
                request.mobility(),
                request.groupList()
        );

        return UpdateScheduleResponse.of(schedule);
    }


}
