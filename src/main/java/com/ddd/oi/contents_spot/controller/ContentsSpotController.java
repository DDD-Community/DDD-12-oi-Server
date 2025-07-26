package com.ddd.oi.contents_spot.controller;

import com.ddd.oi.common.response.CustomApiResponse;
import com.ddd.oi.contents_spot.dto.ContentsSpotRequest;
import com.ddd.oi.contents_spot.dto.ContentsSpotResponse;
import com.ddd.oi.contents_spot.service.ContentsSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1/contents/{contentsId}/spots")
@RequiredArgsConstructor
@Tag(name = "컨텐츠 스팟", description = "컨텐츠 스팟 관련 API")
public class ContentsSpotController {
    private final ContentsSpotService contentsSpotService;

    @PostMapping
    @Operation(summary = "스팟 생성", description = "컨텐츠에 스팟을 추가합니다.", requestBody = @RequestBody(content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "ContentsSpotRequest 예시", value = "{\n  \"spotName\": \"남산타워\",\n  \"address\": \"서울 중구 남산공원길 105\",\n  \"spotDescription\": \"서울의 대표적인 관광 명소입니다.\",\n  \"spotImage\": \"https://example.com/image.jpg\",\n  \"latitude\": 37.5511694,\n  \"longitude\": 126.9882266\n}"))))
    public CustomApiResponse<ContentsSpotResponse> createSpot(@PathVariable Long contentsId,
            @org.springframework.web.bind.annotation.RequestBody ContentsSpotRequest request) {
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
