package com.ddd.oi.schedule.dto.response;

import lombok.Getter;

@Getter
public class CreateScheduleResponse {
    private Long id;

    private CreateScheduleResponse(Long id) {
        this.id = id;
    }

    public static CreateScheduleResponse of(Long id) {
        return new CreateScheduleResponse(id);
    }
}
