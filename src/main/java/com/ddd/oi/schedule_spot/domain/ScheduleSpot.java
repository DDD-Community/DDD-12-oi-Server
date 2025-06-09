package com.ddd.oi.schedule_spot.domain;

import com.ddd.oi.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "schedule_spot")
@Getter
@Setter
@NoArgsConstructor
public class ScheduleSpot extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "schedule_spot_id")
	private Long scheduleSpotId;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "latitude", nullable = false)
	private Double latitude;

	@Column(name = "longitude", nullable = false)
	private Double longitude;
}
