package com.ddd.oi.schedule.domain;

import com.ddd.oi.common.domain.BaseEntity;
import com.ddd.oi.user.domain.User;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "schedule")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Schedule extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "schedule_id")
	private Long scheduleId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "schedule_title", nullable = false)
	private String scheduleTitle;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "mobility", nullable = false)
	private Mobility mobility;

	@Column(name = "first_group",nullable = false)
	private String firstGroup;

	@Column(name = "second_group")
	private String secondGroup;

	@Column(name = "third_group")
	private String thirdGroup;


	public enum Mobility {
		WALK, CAR, PUBLIC_TRANSPORT, BICYCLE
	}

	public enum ScheduleTag {
		TRIP, DAILY,DATE, BUSINESS, OTHER
	}

    public void updateSchedule(String title, LocalDate startDate, LocalDate endDate, Mobility mobility, List<String> groupList) {
        this.scheduleTitle = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.mobility = mobility;
        this.firstGroup = groupList.get(0);
        this.secondGroup = groupList.size() > 1 ? groupList.get(1) : null;
        this.thirdGroup = groupList.size() > 2 ? groupList.get(2) : null;
    }


    public List<String> getGroupList() {
        List<String> result = new ArrayList<>();
        result.add(firstGroup);
        if (secondGroup != null) result.add(secondGroup);
        if (thirdGroup != null) result.add(thirdGroup);
        return result;
    }


}
