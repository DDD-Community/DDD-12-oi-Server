package com.ddd.oi.schedule.dto.request;

import com.ddd.oi.common.annotation.NotBlankNullable;
import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.schedule.domain.Schedule;
import com.ddd.oi.schedule.domain.Schedule.Mobility;
import com.ddd.oi.schedule.domain.Schedule.ScheduleTag;
import com.ddd.oi.user.domain.User;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

public record CreateScheduleRequest(
        @NotBlankNullable(message = "스케줄 제목은 필수입니다.")
        String title,
        @NotBlankNullable(message = "시작 날짜를 정해주세요.")
        LocalDate startDate,
        @NotBlankNullable(message = "스케줄 제목은 필수입니다.")
        LocalDate endDate,
        @NotBlankNullable(message = "이동수단을 정해주세요.")
        Mobility mobility,
        @NotBlankNullable(message = "태그를 정해주세요.")
        ScheduleTag scheduleTag,
        @NotEmpty(message = "하나 이상의 일행을 정해주세요.")
        List<String> groupList
) {
    public CreateScheduleRequest {
        if (startDate != null && endDate != null) {
            if (endDate.isBefore(startDate)) {
                throw new OiException(ErrorCode.END_DATE_BEFORE_START_DATE);
            }
            if (endDate.isAfter(startDate.plusDays(3))) {
                throw new OiException(ErrorCode.INVALID_DATE_RANGE);
            }
        }
        if (groupList != null && groupList.size() != groupList.stream().distinct().count()) {
            throw new OiException(ErrorCode.BAD_REQUEST);
        }
    }
    public Schedule toEntity(User user) {

        String firstGroup = groupList.get(0);
        String secondGroup = groupList.size() > 1 ? groupList.get(1) : null;
        String thirdGroup = groupList.size() > 2 ? groupList.get(2) : null;

        return Schedule.builder()
                .user(user)
                .scheduleTitle(this.title)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .mobility(this.mobility)
                .firstGroup(firstGroup)
                .secondGroup(secondGroup)
                .thirdGroup(thirdGroup)
                .build();
    }
}
