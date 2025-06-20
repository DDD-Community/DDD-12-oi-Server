package com.ddd.oi.schedule.domain;

import com.ddd.oi.common.domain.BaseEntity;
import com.ddd.oi.schedule.domain.enumType.GroupTag;
import com.ddd.oi.schedule.domain.enumType.Mobility;
import com.ddd.oi.schedule.domain.enumType.ScheduleTag;
import com.ddd.oi.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "schedule")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Schedule extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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

	@ElementCollection(targetClass = GroupTag.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "schedule_group", joinColumns = @JoinColumn(name = "schedule_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "group_name", nullable = false)
	@Builder.Default
	private List<GroupTag> groups = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	@Column(name = "schedule_tag", nullable = false)
	private ScheduleTag scheduleTag;

	public void updateSchedule(String title, LocalDate startDate, LocalDate endDate, Mobility mobility,
			List<GroupTag> groups) {
		this.scheduleTitle = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.mobility = mobility;
		this.groups = groups;
	}

	public List<GroupTag> getGroups() {
		return this.groups;
	}

}
