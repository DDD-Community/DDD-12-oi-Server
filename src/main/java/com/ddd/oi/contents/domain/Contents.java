package com.ddd.oi.contents.domain;

import com.ddd.oi.common.domain.BaseEntity;
import com.ddd.oi.contents.domain.enumType.ContentsTag;
import com.ddd.oi.contents_image.domain.ContentsImage;
import com.ddd.oi.contents_spot.domain.ContentsSpot;
import com.ddd.oi.contents.dto.ContentsUpdateRequest;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "contents")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)

public class Contents extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "display_description", columnDefinition = "TEXT")
	private String displayDescription;

	@Column(name = "cost")
	private Integer cost;

	@Column(name = "recommended_schedule")
	private String recommendedSchedule;

	@Column(name = "duration")
	private Integer duration;

	@Enumerated(EnumType.STRING)
	@Column(name = "contents_tag", nullable = false)
	private ContentsTag contentsTag;

	@Column(name = "short_title")
	private String shortTitle;

	@Column(name = "short_description")
	private String shortDescription;

	@OneToMany(mappedBy = "contents", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<ContentsImage> images = new ArrayList<>();

	@OneToMany(mappedBy = "contents", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<ContentsSpot> spots = new ArrayList<>();

	public void update(ContentsUpdateRequest request) {
		this.title = request.title();
		this.displayDescription = request.displayDescription();
		this.cost = request.cost();
		this.recommendedSchedule = request.recommendedSchedule();
		this.duration = request.duration();
		this.contentsTag = request.contentsTag();
		this.shortTitle = request.shortTitle();
		this.shortDescription = request.shortDescription();
	}
}
