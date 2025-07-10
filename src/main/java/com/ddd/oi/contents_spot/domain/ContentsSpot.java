package com.ddd.oi.contents_spot.domain;

import com.ddd.oi.common.domain.BaseEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contents_spot")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ContentsSpot extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contents_spot_id")
	private Long contentsSpotId;

	@Column(name = "spot_name", nullable = false)
	private String spotName;

	@Column(name = "address")
	private String address;

	@Column(name = "spot_description", columnDefinition = "TEXT")
	private String spotDescription;

	@Column(name = "spot_image")
	private String spotImage;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

	public void update(com.ddd.oi.contents_spot.dto.ContentsSpotRequest request) {
		this.spotName = request.spotName();
		this.address = request.address();
		this.spotDescription = request.spotDescription();
		this.spotImage = request.spotImage();
		this.latitude = request.latitude();
		this.longitude = request.longitude();
	}
}
