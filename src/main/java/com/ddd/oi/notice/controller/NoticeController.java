package com.ddd.oi.notice.controller;

import com.ddd.oi.common.response.PageResponse;
import com.ddd.oi.notice.controller.docs.NoticeControllerDocs;
import com.ddd.oi.notice.dto.*;
import com.ddd.oi.notice.service.NoticeService;
import com.ddd.oi.common.response.CustomApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController implements NoticeControllerDocs {

    private final NoticeService noticeService;

    @PostMapping
    public CustomApiResponse<NoticeResponse> createNotice(@RequestBody @Valid NoticeCreateRequest request) {
        return CustomApiResponse.success(noticeService.createNotice(request), 200, "공지사항 생성 성공");
    }

    @PutMapping("/{id}")
    public CustomApiResponse<NoticeResponse> updateNotice(@PathVariable Long id, @RequestBody @Valid NoticeUpdateRequest request) {
        return CustomApiResponse.success(noticeService.updateNotice(id, request), 200, "공지사항 수정 성공");
    }

    @GetMapping
    public CustomApiResponse<PageResponse<NoticeResponse>> getNotices(
        @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return CustomApiResponse.success(noticeService.getNotices(pageable), 200, "공지사항 목록 조회 성공");
    }

    @GetMapping("/{id}")
    public CustomApiResponse<NoticeResponse> getNotice(@PathVariable Long id) {
        return CustomApiResponse.success(noticeService.getNotice(id), 200, "공지사항 조회 성공");
    }

    @DeleteMapping("/{id}")
    public CustomApiResponse<Long> deleteNotice(@PathVariable Long id) {
        return CustomApiResponse.success(noticeService.deleteNotice(id), 200, "공지사항 삭제 성공");
    }
}
