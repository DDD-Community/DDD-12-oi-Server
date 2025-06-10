package com.ddd.oi.schedule.dto.request;

import com.ddd.oi.schedule.domain.Schedule;
import com.ddd.oi.schedule.domain.Schedule.Mobility;
import com.ddd.oi.schedule.domain.Schedule.ScheduleTag;
import com.ddd.oi.user.domain.User;
import java.time.LocalDate;
import java.util.List;

public record CreateScheduleRequest(
        String title,
        LocalDate startDate,
        LocalDate endDate,
        Mobility mobility,
        ScheduleTag scheduleTag,
        List<String> groupList
) {
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
