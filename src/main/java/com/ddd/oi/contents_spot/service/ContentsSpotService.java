package com.ddd.oi.contents_spot.service;

import com.ddd.oi.common.config.CategoryMapping;
import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.contents.domain.Contents;
import com.ddd.oi.contents.repository.ContentsRepository;
import com.ddd.oi.contents_spot.domain.ContentsSpot;
import com.ddd.oi.contents_spot.dto.ContentsSpotRequest;
import com.ddd.oi.contents_spot.dto.ContentsSpotResponse;
import com.ddd.oi.contents_spot.repository.ContentsSpotRepository;
import com.ddd.oi.schedule_detail.domain.ScheduleDetail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentsSpotService {
    private final ContentsSpotRepository contentsSpotRepository;
    private final ContentsRepository contentsRepository;
    private final CategoryMapping categoryMapping;

    @Transactional
    public ContentsSpotResponse createSpot(Long contentsId, ContentsSpotRequest request) {
        Contents contents = contentsRepository.findById(contentsId)
                .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));
        ContentsSpot spot = request.toEntity(contents);

        String mainCategory = categoryMapping.mapToMainCategory(request.category());
        spot.setCategory(mainCategory);
        contents.getSpots().add(spot);
        contentsSpotRepository.save(spot);
        return ContentsSpotResponse.from(spot);
    }

    @Transactional(readOnly = true)
    public ContentsSpotResponse getSpot(Long spotId) {
        ContentsSpot spot = contentsSpotRepository.findById(spotId)
                .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));
        return ContentsSpotResponse.from(spot);
    }

    @Transactional
    public ContentsSpotResponse updateSpot(Long spotId, ContentsSpotRequest request) {
        ContentsSpot spot = contentsSpotRepository.findById(spotId)
                .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));
        String mainCategory = categoryMapping.mapToMainCategory(request.category());
        spot.update(request,mainCategory);
        return ContentsSpotResponse.from(spot);
    }

    @Transactional
    public void deleteSpot(Long spotId) {
        if (!contentsSpotRepository.existsById(spotId)) {
            throw new OiException(ErrorCode.ENTITY_NOT_FOUND);
        }
        contentsSpotRepository.deleteById(spotId);
    }

    @Transactional(readOnly = true)
    public List<ContentsSpotResponse> getSpotsByContentsId(Long contentsId) {
        return contentsSpotRepository.findAll().stream()
                .map(ContentsSpotResponse::from)
                .toList();
    }
}
