package com.ddd.oi.contents.service;

import com.ddd.oi.contents.domain.Contents;
import com.ddd.oi.contents.domain.enumType.ContentsTag;
import com.ddd.oi.contents.dto.*;
import com.ddd.oi.contents.repository.ContentsRepository;
import com.ddd.oi.contents_image.domain.ContentsImage;
import com.ddd.oi.contents_image.repository.ContentsImageRepository;

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
	private final ContentsImageRepository contentsImageRepository;

	@Transactional
	public ContentsResponse createContents(ContentsCreateRequest request) {
		Contents contents = request.toEntity();
		if (request.imageIds() != null && !request.imageIds().isEmpty()) {
			List<ContentsImage> images = contentsImageRepository.findAllById(request.imageIds());
			for (ContentsImage image : images) {
				image.setContents(contents);
			}
			contents.getImages().addAll(images);
		}
		return ContentsResponse.from(contentsRepository.save(contents));
	}

	@Transactional(readOnly = true)
	public ContentsResponse getContents(Long id) {
		Contents contents = contentsRepository.findByIdWithImagesAndSpots(id)
			.orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));

        // 조회수 증가
        contents.incrementViewCount();

		return ContentsResponse.from(contents);
	}

	@Transactional
	public ContentsResponse updateContents(Long id, ContentsUpdateRequest request) {
		Contents contents = contentsRepository.findById(id)
			.orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));

		if (request.recommendationScore() < 0.0 || request.recommendationScore() > 10.0){
            throw new OiException(ErrorCode.INVALID_RECOMMENDATION_SCORE);
        }
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
    public List<ContentsResponse> getContentsListByTagAndSort(ContentsTag tag, String sortBy) {
        List<Contents> contentsList = getContentsBySort(tag, sortBy);
        return contentsList.stream().map(ContentsResponse::from).toList();
    }

    private List<Contents> getContentsBySort(ContentsTag tag, String sortBy) {
        if (tag != null) {
            return switch (sortBy) {
                case "popular" -> contentsRepository.findByContentsTagOrderByViewCountDesc(tag);
                case "recommended" -> contentsRepository.findByContentsTagOrderByRecommendationScoreDesc(tag);
                case "latest" -> contentsRepository.findByContentsTagOrderByCreatedAtDesc(tag);
                default -> throw new OiException(ErrorCode.PARAMETER_INVALID);
            };
        } else {
            return switch (sortBy) {
                case "popular" -> contentsRepository.findAllByOrderByViewCountDesc();
                case "recommended" -> contentsRepository.findAllByOrderByRecommendationScoreDesc();
                case "latest" -> contentsRepository.findAllByOrderByCreatedAtDesc();
                default -> throw new OiException(ErrorCode.PARAMETER_INVALID);
            };
        }
    }

	@Transactional(readOnly = true)
	public Page<ContentsResponse> getContentsPage(Pageable pageable) {
		return contentsRepository.findAll(pageable).map(ContentsResponse::from);
	}
}
