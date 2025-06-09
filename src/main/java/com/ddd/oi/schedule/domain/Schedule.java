package com.ddd.oi.schedule.domain;

import com.ddd.oi.common.domain.BaseEntity;
import com.ddd.oi.user.domain.User;
import com.ddd.oi.grouptag.domain.GroupTag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "schedule")
@Getter
@Setter
@NoArgsConstructor
public class Schedule extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "schedule_id")
	private Long scheduleId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_tag_id", nullable = false)
	private GroupTag groupTag;

	@Column(name = "schedule_title", nullable = false)
	private String scheduleTitle;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "mobility", nullable = false)
	private Mobility mobility;

	public enum Mobility {
		WALK, CAR, PUBLIC_TRANSPORT, BICYCLE
	}
}
