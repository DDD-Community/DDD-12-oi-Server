package com.ddd.oi.contents_spot.controller;

import com.ddd.oi.common.response.CustomApiResponse;
import com.ddd.oi.contents_spot.dto.ContentsSpotRequest;
import com.ddd.oi.contents_spot.dto.ContentsSpotResponse;
import com.ddd.oi.contents_spot.service.ContentsSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contents/{contentsId}/spots")
@RequiredArgsConstructor
public class ContentsSpotController {
    private final ContentsSpotService contentsSpotService;

    @PostMapping
    public CustomApiResponse<ContentsSpotResponse> createSpot(@PathVariable Long contentsId,
            @RequestBody ContentsSpotRequest request) {
        return CustomApiResponse.success(contentsSpotService.createSpot(contentsId, request), 200, "장소 생성 성공");
    }

    @GetMapping("/{spotId}")
    public CustomApiResponse<ContentsSpotResponse> getSpot(@PathVariable Long spotId) {
        return CustomApiResponse.success(contentsSpotService.getSpot(spotId), 200, "장소 조회 성공");
    }

    @PutMapping("/{spotId}")
    public CustomApiResponse<ContentsSpotResponse> updateSpot(@PathVariable Long spotId,
            @RequestBody ContentsSpotRequest request) {
        return CustomApiResponse.success(contentsSpotService.updateSpot(spotId, request), 200, "장소 수정 성공");
    }

    @DeleteMapping("/{spotId}")
    public CustomApiResponse<Void> deleteSpot(@PathVariable Long spotId) {
        contentsSpotService.deleteSpot(spotId);
        return CustomApiResponse.success(null, 200, "장소 삭제 성공");
    }

    @GetMapping
    public CustomApiResponse<List<ContentsSpotResponse>> getSpots(@PathVariable Long contentsId) {
        return CustomApiResponse.success(contentsSpotService.getSpotsByContentsId(contentsId), 200, "장소 목록 조회 성공");
    }
}