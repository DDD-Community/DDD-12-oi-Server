package com.ddd.oi.schedule_detail.domain;

import com.ddd.oi.common.domain.BaseEntity;
import com.ddd.oi.schedule.domain.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "schedule_detail")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ScheduleDetail extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "schedule_detail_id")
	private Long scheduleDetailId;

	@Column(name = "start_time", nullable = false)
	@Schema(type = "string", format = "time", pattern = "HH:mm", example = "14:30")
	@JsonFormat(pattern = "HH:mm")
	private LocalTime startTime;

	@Column(name = "target_date", nullable = false)
	private LocalDate targetDate;

	@Column(name = "memo")
	private String memo;

	@Column(name = "name", nullable = false)
	private String spotName;

	@Column(name = "latitude", nullable = false)
	private Double latitude;

	@Column(name = "longitude", nullable = false)
	private Double longitude;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_id", nullable = false)
	private Schedule schedule;


	public void update(LocalTime startTime, String memo, String spotName, Double latitude, Double longitude) {
		this.startTime = startTime;
		this.memo = memo;
		this.spotName = spotName;
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
