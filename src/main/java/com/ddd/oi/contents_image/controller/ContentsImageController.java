package com.ddd.oi.contents_image.controller;

import com.ddd.oi.common.response.CustomApiResponse;
import com.ddd.oi.contents_image.dto.ContentsImageRequest;
import com.ddd.oi.contents_image.dto.ContentsImageResponse;
import com.ddd.oi.contents_image.service.ContentsImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contents/images")
@RequiredArgsConstructor
@Tag(name = "컨텐츠 이미지", description = "컨텐츠 이미지 관련 API")
public class ContentsImageController {
    private final ContentsImageService contentsImageService;

    @PostMapping
    @Operation(summary = "이미지 정보 생성", description = "S3에 업로드된 이미지 정보를 DB에 저장합니다. contentsId를 입력하면 컨텐츠와 연관관계도 자동 저장됩니다.", requestBody = @RequestBody(content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "ContentsImageRequest 예시", value = "{\n  \"imageUrl\": \"https://oi-s3-bucket.s3.ap-northeast-2.amazonaws.com/images/test-image.jpg\",\n  \"contentsId\": 1\n}"))))
    public CustomApiResponse<ContentsImageResponse> createImage(
            @org.springframework.web.bind.annotation.RequestBody ContentsImageRequest request) {
        return CustomApiResponse.success(contentsImageService.createImage(request), 200, "이미지 정보 생성 성공");
    }

    @GetMapping("/{imageId}")
    @Operation(summary = "이미지 정보 조회", description = "이미지 정보를 조회합니다.")
    public CustomApiResponse<ContentsImageResponse> getImage(@PathVariable Long imageId) {
        return CustomApiResponse.success(contentsImageService.getImage(imageId), 200, "이미지 정보 조회 성공");
    }

    @DeleteMapping("/{imageId}")
    @Operation(summary = "이미지 정보 삭제", description = "이미지 정보를 삭제합니다.")
    public CustomApiResponse<Void> deleteImage(@PathVariable Long imageId) {
        contentsImageService.deleteImage(imageId);
        return CustomApiResponse.success(null, 200, "이미지 정보 삭제 성공");
    }

    @GetMapping
    @Operation(summary = "이미지 목록 조회", description = "모든 이미지 정보를 조회합니다.")
    public CustomApiResponse<List<ContentsImageResponse>> getImages() {
        return CustomApiResponse.success(contentsImageService.getImages(), 200, "이미지 목록 조회 성공");
    }
}