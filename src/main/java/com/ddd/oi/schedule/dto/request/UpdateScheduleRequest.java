package com.ddd.oi.schedule.dto.request;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.schedule.domain.Schedule.Mobility;
import java.time.LocalDate;
import java.util.List;

public record UpdateScheduleRequest(
        String title,
        LocalDate startDate,
        LocalDate endDate,
        Mobility mobility,
        List<String> groupList


) {
    public UpdateScheduleRequest {
        if (startDate != null && endDate != null && endDate.isAfter(startDate.plusDays(3))) {
            throw new OiException(ErrorCode.INVALID_DATE_RANGE);
        }
        if (groupList != null && groupList.size() != groupList.stream().distinct().count()) {
            throw new OiException(ErrorCode.DUPLICATE_GROUP_NAME);
        }
    }

}
