package com.ddd.oi.common.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddd.oi.schedule.domain.enumType.GroupTag;
import com.ddd.oi.schedule.domain.enumType.Mobility;
import com.ddd.oi.schedule.domain.enumType.ScheduleTag;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/enums")
@Tag(name = "공통 ENUM", description = "모든 Enum 타입 값들을 조회합니다.")
public class EnumController {

	@GetMapping("/group-tags")
	@Operation(summary = "GroupTag 목록 조회")
	public List<String> getGroupTags() {
		return Arrays.stream(GroupTag.values())
			.map(Enum::name)
			.toList();
	}

	@GetMapping("/mobility")
	@Operation(summary = "Mobility 목록 조회")
	public List<String> getMobilityTypes() {
		return Arrays.stream(Mobility.values())
			.map(Enum::name)
			.toList();
	}

	@GetMapping("/schedule-tags")
	@Operation(summary = "ScheduleTag 목록 조회")
	public List<String> getScheduleTags() {
		return Arrays.stream(ScheduleTag.values())
			.map(Enum::name)
			.toList();
	}
}
