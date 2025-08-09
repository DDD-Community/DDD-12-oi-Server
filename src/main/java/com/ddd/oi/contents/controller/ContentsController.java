package com.ddd.oi.contents.controller;

import com.ddd.oi.contents.controller.docs.ContentsControllerDocs;
import com.ddd.oi.contents.dto.*;
import com.ddd.oi.contents.service.ContentsService;
import com.ddd.oi.common.response.CustomApiResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/contents")
@RequiredArgsConstructor
public class ContentsController implements ContentsControllerDocs {
	private final ContentsService contentsService;

	@PostMapping
	@Override
	public CustomApiResponse<ContentsResponse> createContents(@RequestBody ContentsCreateRequest request) {
		return CustomApiResponse.success(contentsService.createContents(request), 200, "컨텐츠 생성 성공");
	}

	@GetMapping("/{contentsId}")
	@Override
	public CustomApiResponse<ContentsResponse> getContents(@PathVariable Long contentsId) {
		return CustomApiResponse.success(contentsService.getContents(contentsId), 200, "컨텐츠 조회 성공");
	}

	@PostMapping("/{contentsId}")
	@Override
	public CustomApiResponse<ContentsResponse> updateContents(@PathVariable Long contentsId,
		@RequestBody ContentsUpdateRequest request) {
		return CustomApiResponse.success(contentsService.updateContents(contentsId, request), 200, "컨텐츠 수정 성공");
	}

	@DeleteMapping("/{contentsId}")
	@Override
	public CustomApiResponse<Long> deleteContents(@PathVariable Long contentsId) {
		contentsService.deleteContents(contentsId);
		return CustomApiResponse.success(contentsId, 200, "컨텐츠 삭제 성공");
	}

/*	@GetMapping
	@Override
	public CustomApiResponse<List<ContentsResponse>> getContentsList() {
		return CustomApiResponse.success(contentsService.getContentsWithImagesAndSpots(), 200, "컨텐츠 리스트 조회 성공");
	}*/
	@GetMapping
	@Override
	public CustomApiResponse<List<ContentsResponse>> getContentsByDateRange(
		@RequestParam LocalDate fromDate,
		@RequestParam LocalDate toDate) {
		return CustomApiResponse.success(contentsService.getContentsByDateRange(fromDate, toDate), 200, "컨텐츠 날짜별 조회 성공");
	}
}
