package com.ddd.oi.schedule_detail.domain;

import com.ddd.oi.common.domain.BaseEntity;
import com.ddd.oi.schedule.domain.Schedule;
import com.ddd.oi.schedule_spot.domain.ScheduleSpot;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "schedule_detail")
@Getter
@Setter
@NoArgsConstructor
public class ScheduleDetail extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "schedule_detail_id")
	private Long scheduleDetailId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_id", nullable = false)
	private Schedule schedule;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_spot_id", nullable = false)
	private ScheduleSpot scheduleSpot;

	@Column(name = "start_time", nullable = false)
	private LocalDateTime startTime;

	@Column(name = "memo")
	private String memo;

	@Column(name = "target_date", nullable = false)
	private String targetDate;
}
