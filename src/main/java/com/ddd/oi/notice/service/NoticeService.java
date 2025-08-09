package com.ddd.oi.notice.service;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.notice.domain.Notice;
import com.ddd.oi.notice.dto.*;
import com.ddd.oi.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional
    public NoticeResponse createNotice(NoticeCreateRequest request) {
        Notice notice = Notice.builder()
            .title(request.title())
            .content(request.content())
            .build();
        return NoticeResponse.from(noticeRepository.save(notice));
    }

    @Transactional
    public NoticeResponse updateNotice(Long id, NoticeUpdateRequest request) {
        Notice notice = noticeRepository.findById(id)
            .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));
        notice.update(request.title(), request.content());
        return NoticeResponse.from(notice);
    }

    @Transactional(readOnly = true)
    public Page<NoticeResponse> getNotices(Pageable pageable) {
        return noticeRepository.findAll(pageable)
            .map(NoticeResponse::from);
    }

    @Transactional
    public NoticeResponse getNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
            .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));
        return NoticeResponse.from(notice);
    }

    @Transactional
    public Long deleteNotice(Long id) {
        if (!noticeRepository.existsById(id)) {
            throw new OiException(ErrorCode.ENTITY_NOT_FOUND);
        }
        noticeRepository.deleteById(id);
        return id;
    }
}
