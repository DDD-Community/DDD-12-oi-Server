package com.ddd.oi.faq.service;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.common.response.PageResponse;
import com.ddd.oi.faq.domain.Faq;
import com.ddd.oi.faq.dto.FaqCreateRequest;
import com.ddd.oi.faq.dto.FaqResponse;
import com.ddd.oi.faq.dto.FaqUpdateRequest;
import com.ddd.oi.faq.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FaqService {

    private final FaqRepository faqRepository;

    @Transactional
    public FaqResponse createFaq(FaqCreateRequest request) {
        Faq faq = Faq.builder()
            .title(request.title())
            .content(request.content())
            .build();
        return FaqResponse.from(faqRepository.save(faq));
    }

    @Transactional(readOnly = true)
    public FaqResponse getFaq(Long id) {
        Faq faq = faqRepository.findById(id)
            .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));
        return FaqResponse.from(faq);
    }

    @Transactional
    public FaqResponse updateFaq(Long id, FaqUpdateRequest request) {
        Faq faq = faqRepository.findById(id)
            .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));
        faq.update(request.title(), request.content());
        return FaqResponse.from(faq);
    }

    @Transactional
    public void deleteFaq(Long id) {
        if (!faqRepository.existsById(id)) {
            throw new OiException(ErrorCode.ENTITY_NOT_FOUND);
        }
        faqRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public PageResponse<FaqResponse> getFaqs(Pageable pageable) {
        Page<FaqResponse> page = faqRepository.findAll(pageable).map(FaqResponse::from);

        return new PageResponse<>(page);
    }
}
