package com.ddd.oi.contents.controller;

import com.ddd.oi.contents.dto.*;
import com.ddd.oi.contents.service.ContentsService;
import com.ddd.oi.common.response.CustomApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.util.List;

@RestController
@RequestMapping("/api/v1/contents")
@RequiredArgsConstructor
@Tag(name = "컨텐츠", description = "컨텐츠 관련 API")
public class ContentsController {
    private final ContentsService contentsService;

    @PostMapping
    @Operation(summary = "컨텐츠 생성", description = "컨텐츠를 생성합니다.", requestBody = @RequestBody(content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "ContentsCreateRequest 예시", value = "{\n  \"title\": \"여행지 추천\",\n  \"displayDescription\": \"이곳은 정말 멋진 여행지입니다.\",\n  \"cost\": 10000,\n  \"recommendedSchedule\": \"2박 3일\",\n  \"duration\": 120,\n  \"contentsTag\": \"TRAVEL\",\n  \"shortTitle\": \"여행지\",\n  \"shortDescription\": \"짧은 설명입니다.\",\n  \"imageIds\": [1,2,3]\n}"))))
    public CustomApiResponse<ContentsResponse> createContents(
            @org.springframework.web.bind.annotation.RequestBody ContentsCreateRequest request) {
        return CustomApiResponse.success(contentsService.createContents(request), 200, "컨텐츠 생성 성공");
    }

    @GetMapping("/{contentsId}")
    public CustomApiResponse<ContentsResponse> getContents(@PathVariable Long contentsId) {
        return CustomApiResponse.success(contentsService.getContents(contentsId), 200, "컨텐츠 조회 성공");
    }

    @PutMapping("/{contentsId}")
    public CustomApiResponse<ContentsResponse> updateContents(@PathVariable Long contentsId,
            @RequestBody ContentsUpdateRequest request) {
        return CustomApiResponse.success(contentsService.updateContents(contentsId, request), 200, "컨텐츠 수정 성공");
    }

    @DeleteMapping("/{contentsId}")
    public CustomApiResponse<Void> deleteContents(@PathVariable Long contentsId) {
        contentsService.deleteContents(contentsId);
        return CustomApiResponse.success(null, 200, "컨텐츠 삭제 성공");
    }

    @GetMapping
    @Operation(summary = "컨텐츠 전체 리스트 조회", description = "모든 컨텐츠를 리스트로 조회합니다.")
    public CustomApiResponse<List<ContentsResponse>> getContentsList() {
        return CustomApiResponse.success(contentsService.list(), 200, "컨텐츠 리스트 조회 성공");
    }
}
