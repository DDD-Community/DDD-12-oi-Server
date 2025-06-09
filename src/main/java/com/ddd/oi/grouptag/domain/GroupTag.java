package com.ddd.oi.grouptag.domain;

import com.ddd.oi.common.domain.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "group_tag")
@Getter
@Setter
@NoArgsConstructor
public class GroupTag extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "schedule_tag_id")
	private Long scheduleTagId;

	@Column(name = "group_name", nullable = false)
	private String groupName;
}
