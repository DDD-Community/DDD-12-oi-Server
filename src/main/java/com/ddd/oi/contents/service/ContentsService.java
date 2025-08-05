package com.ddd.oi.contents.service;

import com.ddd.oi.contents.domain.Contents;
import com.ddd.oi.contents.domain.enumType.ContentsTag;
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

	@Transactional
	public ContentsResponse getContents(Long contentsId) {
		Contents contents = contentsRepository.findById(contentsId)
			.orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));

		// 조회수 증가
		contents.incrementViewCount();

		return ContentsResponse.from(contents);
	}

	@Transactional
	public ContentsResponse updateContents(Long contentsId, ContentsUpdateRequest request) {
		Contents contents = contentsRepository.findById(contentsId)
			.orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));

		if (request.recommendationScore() < 0.0 || request.recommendationScore() > 10.0) {
			throw new OiException(ErrorCode.INVALID_RECOMMENDATION_SCORE);
		}
		contents.update(request);
		return ContentsResponse.from(contents);
	}

	@Transactional
	public void deleteContents(Long contentsId) {
		if (!contentsRepository.existsById(contentsId)) {
			throw new OiException(ErrorCode.ENTITY_NOT_FOUND);
		}
		contentsRepository.deleteById(contentsId);
	}

	@Transactional(readOnly = true)
	public List<ContentsResponse> getContentsWithImagesAndSpots() {
		List<Contents> contentsList = contentsRepository.findAll();
		return contentsList.stream()
			.map(ContentsResponse::from)
			.toList();
	}

}
