package com.ddd.oi.schedule.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.schedule.domain.Schedule;
import com.ddd.oi.schedule.domain.enumType.GroupTag;
import com.ddd.oi.schedule.domain.enumType.Mobility;
import com.ddd.oi.schedule.domain.enumType.ScheduleTag;
import com.ddd.oi.schedule.dto.request.CreateScheduleRequest;
import com.ddd.oi.schedule.dto.request.UpdateScheduleRequest;
import com.ddd.oi.schedule.dto.response.CreateScheduleResponse;
import com.ddd.oi.schedule.dto.response.UpdateScheduleResponse;
import com.ddd.oi.schedule.repository.ScheduleRepository;
import com.ddd.oi.user.domain.User;
import com.ddd.oi.user.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

    @InjectMocks
    private ScheduleService scheduleService;
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private UserRepository userRepository;

    private User user;
    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .nickname("test_user")
                .build();

    }

    @Test
    @DisplayName("유저는 스케줄 생성에 성공한다.")
    void 스케줄_생성_성공() {
        //Given
        CreateScheduleRequest request = new CreateScheduleRequest(
                "test_schedule",
                LocalDate.of(2025, 5, 5),
                LocalDate.of(2025, 5, 10),
                Mobility.CAR,
                ScheduleTag.BUSINESS,
                List.of(GroupTag.COUPLE, GroupTag.FRIEND)
        );

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(scheduleRepository.countByUserIdAndDate(eq(user.getId()), any())).thenReturn(0);

        when(scheduleRepository.save(any(Schedule.class)))
                .thenAnswer(invocation -> {
                    Schedule saved = invocation.getArgument(0);
                    java.lang.reflect.Field idField = Schedule.class.getDeclaredField("id");
                    idField.setAccessible(true);
                    idField.set(saved, 1L);
                    return saved;
                });

        //When
        CreateScheduleResponse response = scheduleService.createSchedule(user.getId(), request);

        //Then
        assertThat(response).isNotNull();
        verify(scheduleRepository, times(1)).save(any(Schedule.class));
    }

    @Test
    @DisplayName("하루에 3개 이상의 스케줄이 존재할 경우 예외가 발생한다.")
    void 하루_스케줄_초과_예외() {
        // Given
        CreateScheduleRequest request = new CreateScheduleRequest(
                "test_schedule",
                LocalDate.of(2025, 5, 5),
                LocalDate.of(2025, 5, 7),
                Mobility.CAR,
                ScheduleTag.BUSINESS,
                List.of(GroupTag.COUPLE)
        );

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        when(scheduleRepository.countByUserIdAndDate(eq(user.getId()), any()))
                .thenAnswer(invocation -> {
                    LocalDate date = invocation.getArgument(1);
                    return date.equals(LocalDate.of(2025, 5, 6)) ? 3 : 0;
                });

        // When & Then
        assertThrows(OiException.class, () ->
                scheduleService.createSchedule(user.getId(), request));
    }

    @Test
    @DisplayName("유저가 존재하지 않으면 예외가 발생한다.")
    void 유저_존재하지_않음_예외() {
        // Given
        CreateScheduleRequest request = new CreateScheduleRequest(
                "test_schedule",
                LocalDate.of(2025, 5, 5),
                LocalDate.of(2025, 5, 7),
                Mobility.CAR,
                ScheduleTag.BUSINESS,
                List.of(GroupTag.COUPLE)
        );
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(OiException.class, () ->
                scheduleService.createSchedule(user.getId(), request));
    }
    @Test
    @DisplayName("유저는 스케줄 수정에 성공한다.")
    void 스케줄_수정_성공() {
        //Given
        Schedule schedule = Schedule.builder()
                .id(1L)
                .scheduleTitle("test_schedule")
                .startDate(LocalDate.of(2025,5,5))
                .endDate(LocalDate.of(2025,5,10))
                .mobility(Mobility.CAR)
                .scheduleTag(ScheduleTag.DAILY)
                .groups(List.of(GroupTag.COUPLE))
                .user(user)
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(scheduleRepository.findByUser_IdAndId(user.getId(), schedule.getId()))
                .thenReturn(Optional.of(schedule));

        UpdateScheduleRequest request = new UpdateScheduleRequest(
                "updated_schedule",
                LocalDate.of(2025, 5, 6),
                LocalDate.of(2025, 5, 12),
                Mobility.WALK,
                ScheduleTag.BUSINESS,
                List.of(GroupTag.FRIEND.name(), GroupTag.CHILDREN.name())
        );
        //When
        UpdateScheduleResponse response = scheduleService.updateSchedule(user.getId(), schedule.getId(), request);

        //Then
        assertThat(response).isNotNull();
        assertThat(response.scheduleId()).isEqualTo(schedule.getId());
        assertThat(schedule.getScheduleTitle()).isEqualTo("updated_schedule");
        assertThat(schedule.getStartDate()).isEqualTo(LocalDate.of(2025, 5, 6));
        assertThat(schedule.getEndDate()).isEqualTo(LocalDate.of(2025, 5, 12));
        assertThat(schedule.getMobility()).isEqualTo(Mobility.WALK);
        assertThat(schedule.getScheduleTag()).isEqualTo(ScheduleTag.BUSINESS);
        assertThat(schedule.getGroups()).isEqualTo(List.of(GroupTag.FRIEND,GroupTag.CHILDREN));
    }
    @Test
    @DisplayName("유저가 없을시 스케줄 수정에 실패한다.")
    void 유저_없을시_스케줄_수정_예외() {
        //Given
        Schedule schedule = Schedule.builder()
                .id(1L)
                .scheduleTitle("test_schedule")
                .startDate(LocalDate.of(2025,5,5))
                .endDate(LocalDate.of(2025,5,10))
                .mobility(Mobility.CAR)
                .scheduleTag(ScheduleTag.DAILY)
                .groups(List.of(GroupTag.COUPLE))
                .user(user)
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        UpdateScheduleRequest request = new UpdateScheduleRequest(
                "updated_schedule",
                LocalDate.of(2025, 5, 6),
                LocalDate.of(2025, 5, 12),
                Mobility.WALK,
                ScheduleTag.BUSINESS,
                List.of(GroupTag.FRIEND.name(), GroupTag.CHILDREN.name())
        );
        //When & Then
        assertThrows(OiException.class, () ->
                scheduleService.updateSchedule(user.getId(), schedule.getId(),request));
    }
    @Test
    @DisplayName("스케줄이 없을 시 스케줄 수정에 실패한다.")
    void 스케줄_없을시_수정실패() {
        //Given
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(scheduleRepository.findByUser_IdAndId(user.getId(),1L))
                .thenReturn(Optional.empty());

        UpdateScheduleRequest request = new UpdateScheduleRequest(
                "updated_schedule",
                LocalDate.of(2025, 5, 6),
                LocalDate.of(2025, 5, 12),
                Mobility.WALK,
                ScheduleTag.BUSINESS,
                List.of(GroupTag.FRIEND.name(), GroupTag.CHILDREN.name())
        );

        //When & Then
        assertThrows(OiException.class, () ->
                scheduleService.updateSchedule(user.getId(),1L,request));
    }
    @Test
    @DisplayName("스케줄 삭제에 성공한다.")
    void 스케줄_삭제_성공() {
         //Given
        Schedule schedule = Schedule.builder()
                .id(1L)
                .scheduleTitle("test_schedule")
                .user(user)
                .startDate(LocalDate.of(2025,5,5))
                .endDate(LocalDate.of(2025,5,20))
                .mobility(Mobility.CAR)
                .scheduleTag(ScheduleTag.DAILY)
                .groups(List.of(GroupTag.COUPLE,GroupTag.FRIEND))
                .build();


        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(scheduleRepository.findByUser_IdAndId(user.getId(), schedule.getId()))
                .thenReturn(Optional.of(schedule));
        //When
        boolean result = scheduleService.deleteSchedule(user.getId(), schedule.getId());
        //Then
        verify(scheduleRepository).delete(schedule);
        assertThat(result).isTrue();
    }
    @Test
    @DisplayName("유저가 없을 시 스케줄 삭제에 실패한다.")
    void 유저_없을시_스케줄_삭제_실패() {
        //Given
        Schedule schedule = Schedule.builder()
                .id(1L)
                .scheduleTitle("test_schedule")
                .user(user)
                .startDate(LocalDate.of(2025,5,5))
                .endDate(LocalDate.of(2025,5,20))
                .mobility(Mobility.CAR)
                .scheduleTag(ScheduleTag.DAILY)
                .groups(List.of(GroupTag.COUPLE,GroupTag.FRIEND))
                .build();
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        //When&Then
        assertThrows(OiException.class, () ->
                scheduleService.deleteSchedule(user.getId(), 1L));
    }
    @Test
    @DisplayName("스케줄 없을 시 스케줄 삭제에 실패한다.")
    void 스케줄_없을시_스케줄_삭제_실패() {
        //Given
        Schedule schedule = Schedule.builder()
                .id(1L)
                .scheduleTitle("test_schedule")
                .user(user)
                .startDate(LocalDate.of(2025,5,5))
                .endDate(LocalDate.of(2025,5,20))
                .mobility(Mobility.CAR)
                .scheduleTag(ScheduleTag.DAILY)
                .groups(List.of(GroupTag.COUPLE,GroupTag.FRIEND))
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(scheduleRepository.findByUser_IdAndId(user.getId(),schedule.getId()))
                .thenReturn(Optional.empty());
        //When&Then
        assertThrows(OiException.class, () ->
                scheduleService.deleteSchedule(user.getId(),schedule.getId()));
    }
}

