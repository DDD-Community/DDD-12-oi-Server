package com.ddd.oi.contents_spot.domain;

import com.ddd.oi.common.domain.BaseEntity;
import com.ddd.oi.contents.domain.Contents;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contents_spot")
@Getter
@Setter
@NoArgsConstructor
public class ContentsSpot extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contents_spot_id")
	private Long contentsSpotId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contents_id", nullable = false)
	private Contents contents;

	@Column(name = "spot_name", nullable = false)
	private String spotName;

	@Column(name = "address")
	private String address;

	@Column(name = "spot_description", columnDefinition = "TEXT")
	private String spotDescription;

	@Column(name = "spot_image")
	private String spotImage;
}
