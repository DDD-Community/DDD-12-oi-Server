package com.ddd.oi.contents_spot.service;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.contents.domain.Contents;
import com.ddd.oi.contents.repository.ContentsRepository;
import com.ddd.oi.contents_spot.domain.ContentsSpot;
import com.ddd.oi.contents_spot.dto.ContentsSpotRequest;
import com.ddd.oi.contents_spot.dto.ContentsSpotResponse;
import com.ddd.oi.contents_spot.repository.ContentsSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentsSpotService {
    private final ContentsSpotRepository contentsSpotRepository;
    private final ContentsRepository contentsRepository;

    @Transactional
    public ContentsSpotResponse createSpot(Long contentsId, ContentsSpotRequest request) {
        Contents contents = contentsRepository.findById(contentsId)
                .orElseThrow(() -> new OiException(ErrorCode.ENTITY_NOT_FOUND));
        ContentsSpot spot = ContentsSpot.builder()
                .spotName(request.spotName())
                .address(request.address())
                .spotDescription(request.spotDescription())
                .spotImage(request.spotImage())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .build();
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
        spot.update(request);
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