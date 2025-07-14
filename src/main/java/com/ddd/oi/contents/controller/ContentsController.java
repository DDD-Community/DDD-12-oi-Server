package com.ddd.oi.contents.controller;

import com.ddd.oi.contents.dto.*;
import com.ddd.oi.contents.service.ContentsService;
import com.ddd.oi.common.response.CustomApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentsController {
    private final ContentsService contentsService;

    @PostMapping
    public CustomApiResponse<ContentsResponse> createContents(@RequestBody ContentsCreateRequest request) {
        return CustomApiResponse.success(contentsService.createContents(request), 200, "컨텐츠 생성 성공");
    }

    @GetMapping("/{contentsId}")
    public CustomApiResponse<ContentsResponse> getContents(@PathVariable Long contentsId) {
        return CustomApiResponse.success(contentsService.getContents(contentsId), 200, "컨텐츠 조회 성공");
    }

    @PutMapping("/{contentsId}")
    public CustomApiResponse<ContentsResponse> updateContents(@PathVariable Long contentsId,
            @RequestBody ContentsUpdateRequest request) {
        return CustomApiResponse.success(contentsService.updateContents(contentsId, request), 200, "컨텐츠 수정 성공");
    }

    @DeleteMapping("/{contentsId}")
    public CustomApiResponse<Void> deleteContents(@PathVariable Long contentsId) {
        contentsService.deleteContents(contentsId);
        return CustomApiResponse.success(null, 200, "컨텐츠 삭제 성공");
    }

    @GetMapping
    public CustomApiResponse<Page<ContentsResponse>> getContentsPage(@PageableDefault(size = 10) Pageable pageable) {
        return CustomApiResponse.success(contentsService.getContentsPage(pageable), 200, "컨텐츠 페이징 조회 성공");
    }
}