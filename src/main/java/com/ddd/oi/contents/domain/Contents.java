package com.ddd.oi.contents.domain;


import com.ddd.oi.common.domain.BaseEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "contents")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)

public class Contents extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contents_id")
	private Long contentsId;

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

	public enum ContentsTag {
		FOOD, TOURIST_ATTRACTION, ACCOMMODATION, ACTIVITY, SHOPPING, CULTURE
	}
}
