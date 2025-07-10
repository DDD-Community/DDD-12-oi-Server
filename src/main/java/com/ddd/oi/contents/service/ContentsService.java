package com.ddd.oi.contents.service;

import com.ddd.oi.contents.domain.Contents;
import com.ddd.oi.contents.dto.*;
import com.ddd.oi.contents.repository.ContentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;

@Service
@RequiredArgsConstructor
public class ContentsService {
    private final ContentsRepository contentsRepository;

    @Transactional
    public ContentsResponse createContents(ContentsCreateRequest request) {
        Contents contents = request.toEntity();
        return ContentsResponse.from(contentsRepository.save(contents));
    }

    @Transactional(readOnly = true)
    public ContentsResponse getContents(Long id) {
        Contents contents = contentsRepository.findByIdWithImagesAndSpots(id)
                .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));
        return ContentsResponse.from(contents);
    }

    @Transactional
    public ContentsResponse updateContents(Long id, ContentsUpdateRequest request) {
        Contents contents = contentsRepository.findByIdWithImagesAndSpots(id)
                .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));
        contents.update(request);
        return ContentsResponse.from(contents);
    }

    @Transactional
    public void deleteContents(Long id) {
        if (!contentsRepository.existsById(id)) {
            throw new OiException(ErrorCode.ENTITY_NOT_FOUND);
        }
        contentsRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ContentsResponse> list() {
        return contentsRepository.findAll().stream().map(ContentsResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public Page<ContentsResponse> getContentsPage(Pageable pageable) {
        return contentsRepository.findAll(pageable).map(ContentsResponse::from);
    }
}