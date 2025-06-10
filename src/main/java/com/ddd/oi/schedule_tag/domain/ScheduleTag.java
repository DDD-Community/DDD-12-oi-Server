package com.ddd.oi.schedule_tag.domain;

import com.ddd.oi.common.domain.BaseEntity;
import com.ddd.oi.schedule.domain.Schedule;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "schedule_tag")
@Getter
@Setter
@NoArgsConstructor
public class ScheduleTag extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "schedule_tag_id")
	private Long scheduleTagId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_id", nullable = false)
	private Schedule schedule;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "color")
	private String color;
}
