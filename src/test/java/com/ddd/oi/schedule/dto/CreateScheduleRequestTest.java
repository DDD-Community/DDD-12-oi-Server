package com.ddd.oi.schedule.dto;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.schedule.domain.enumType.GroupTag;
import com.ddd.oi.schedule.domain.enumType.Mobility;
import com.ddd.oi.schedule.domain.enumType.ScheduleTag;
import com.ddd.oi.schedule.dto.request.CreateScheduleRequest;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateScheduleRequestTest {

    @Test
    @DisplayName("종료일이 시작일보다 빠르면 예외 발생")
    void 종료일_시작일_검증() {
        assertThrows(OiException.class, () -> new CreateScheduleRequest(
                "test_schedule",
                LocalDate.of(2025, 6, 10),
                LocalDate.of(2025, 6, 5),
                Mobility.CAR,
                ScheduleTag.BUSINESS,
                List.of(GroupTag.FRIEND)
        ));
    }

    @Test
    @DisplayName("그룹이 중복되면 예외 발생")
    void 그룹_중복_검증() {
        assertThrows(OiException.class, () -> new CreateScheduleRequest(
                "test_schedule",
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 2),
                Mobility.CAR,
                ScheduleTag.BUSINESS,
                List.of(GroupTag.FRIEND, GroupTag.FRIEND)
        ));
    }

}
