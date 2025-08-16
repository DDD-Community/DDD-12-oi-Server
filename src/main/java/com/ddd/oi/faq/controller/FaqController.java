package com.ddd.oi.faq.controller;

import com.ddd.oi.common.response.CustomApiResponse;
import com.ddd.oi.common.response.PageResponse;
import com.ddd.oi.faq.controller.docs.FaqControllerDocs;
import com.ddd.oi.faq.dto.FaqCreateRequest;
import com.ddd.oi.faq.dto.FaqResponse;
import com.ddd.oi.faq.dto.FaqUpdateRequest;
import com.ddd.oi.faq.service.FaqService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/faqs")
@RequiredArgsConstructor
public class FaqController implements FaqControllerDocs {

	private final FaqService faqService;

	@PostMapping
	public CustomApiResponse<FaqResponse> createFaq(@RequestBody FaqCreateRequest request) {
		return CustomApiResponse.success(faqService.createFaq(request), 200, "FAQ 생성 성공");
	}

	@GetMapping("/{id}")
	public CustomApiResponse<FaqResponse> getFaq(@PathVariable Long id) {
		return CustomApiResponse.success(faqService.getFaq(id), 200, "FAQ 조회 성공");
	}

	@PutMapping("/{id}")
	public CustomApiResponse<FaqResponse> updateFaq(@PathVariable Long id, @RequestBody FaqUpdateRequest request) {
		return CustomApiResponse.success(faqService.updateFaq(id, request), 200, "FAQ 수정 성공");
	}

	@DeleteMapping("/{id}")
	public CustomApiResponse<Long> deleteFaq(@PathVariable Long id) {
		faqService.deleteFaq(id);
		return CustomApiResponse.success(id, 200, "FAQ 삭제 성공");
	}

	@GetMapping
	public CustomApiResponse<PageResponse<FaqResponse>> getFaqs(
		@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
		@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		return CustomApiResponse.success(faqService.getFaqs(pageable), 200, "FAQ 목록 조회 성공");
	}
}
