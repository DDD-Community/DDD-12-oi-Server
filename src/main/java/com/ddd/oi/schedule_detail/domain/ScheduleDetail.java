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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ScheduleDetail extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "start_time")
	@Schema(type = "string", format = "time", pattern = "HH:mm", example = "14:30")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
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

	@Column(name = "category", nullable = false)
	private String category;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_id", nullable = false)
	private Schedule schedule;

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public void update(LocalTime startTime,LocalDate targetDate,String memo, String spotName, Double latitude, Double longitude,String category) {
		this.startTime = startTime;
		this.targetDate = targetDate;
		this.memo = memo;
		this.spotName = spotName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.category = category;
	}
}
