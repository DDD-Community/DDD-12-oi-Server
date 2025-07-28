package com.ddd.oi.contents_image.service;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.contents_image.domain.ContentsImage;
import com.ddd.oi.contents_image.dto.ContentsImageRequest;
import com.ddd.oi.contents_image.dto.ContentsImageResponse;
import com.ddd.oi.contents_image.repository.ContentsImageRepository;
import com.ddd.oi.contents.domain.Contents;
import com.ddd.oi.contents.repository.ContentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentsImageService {
    private final ContentsImageRepository contentsImageRepository;
    private final ContentsRepository contentsRepository;

    @Transactional
    public ContentsImageResponse createImage(ContentsImageRequest request) {
        ContentsImage.ContentsImageBuilder builder = ContentsImage.builder()
                .imageUrl(request.imageUrl());
        ContentsImage contentsImage = builder.build();
        if (request.contentsId() != null) {
            Contents contents = contentsRepository.findById(request.contentsId())
                    .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));
            contentsImage.setContents(contents);
            contents.getImages().add(contentsImage);
        }
        return ContentsImageResponse.from(contentsImageRepository.save(contentsImage));
    }

    @Transactional(readOnly = true)
    public ContentsImageResponse getImage(Long imageId) {
        ContentsImage contentsImage = contentsImageRepository.findById(imageId)
                .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));
        return ContentsImageResponse.from(contentsImage);
    }

    @Transactional
    public void deleteImage(Long imageId) {
        if (!contentsImageRepository.existsById(imageId)) {
            throw new OiException(ErrorCode.ENTITY_NOT_FOUND);
        }
        contentsImageRepository.deleteById(imageId);
    }

    @Transactional(readOnly = true)
    public List<ContentsImageResponse> getImages() {
        return contentsImageRepository.findAll().stream()
                .map(ContentsImageResponse::from)
                .toList();
    }
}