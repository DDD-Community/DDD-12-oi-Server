package com.ddd.oi.contents_spot.controller.docs;

import com.ddd.oi.common.response.CustomApiResponse;
import com.ddd.oi.contents_spot.dto.ContentsSpotRequest;
import com.ddd.oi.contents_spot.dto.ContentsSpotResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "컨텐츠 스팟", description = "컨텐츠 스팟 관련 API")
public interface ContentsSpotControllerDocs {

	@Operation(summary = "스팟 생성", description = "컨텐츠에 스팟을 추가합니다.", requestBody = @RequestBody(content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "ContentsSpotRequest 예시", value = "{\n  \"spotName\": \"남산타워\",\n  \"address\": \"서울 중구 남산공원길 105\",\n  \"spotDescription\": \"서울의 대표적인 관광 명소입니다.\",\n  \"spotImage\": \"test-image.jpg\",\n  \"latitude\": 37.5511694,\n  \"longitude\": 126.9882266\n}"))), responses = @ApiResponse(responseCode = "200", description = "스팟 생성 성공", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "성공 응답 예시", value = "{\n  \"statusCode\": 200,\n  \"resultType\": \"SUCCESS\",\n  \"data\": {\n    \"id\": 3,\n    \"spotName\": \"남산타워\",\n    \"address\": \"서울 중구 남산공원길 105\",\n    \"spotDescription\": \"서울의 대표적인 관광 명소입니다.\",\n    \"spotImage\": \"https://ddd-oi.store/api/v1/s3/images/test-image.jpg\",\n    \"latitude\": 37.5511694,\n    \"longitude\": 126.9882266\n  },\n  \"message\": \"스팟 생성 성공\"\n}"))))
	CustomApiResponse<ContentsSpotResponse> createSpot(Long contentsId, ContentsSpotRequest request);

	@Operation(summary = "스팟 조회", description = "스팟 ID를 통해 스팟을 조회합니다.", responses = @ApiResponse(responseCode = "200", description = "스팟 조회 성공", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "성공 응답 예시", value = "{\n  \"statusCode\": 200,\n  \"resultType\": \"SUCCESS\",\n  \"data\": {\n    \"id\": 3,\n    \"spotName\": \"경복궁\",\n    \"address\": \"서울특별시 종로구 사직로 161 경복궁\",\n    \"spotDescription\": \"서울의 대표적인 관광 명소입니다.\",\n    \"spotImage\": \"https://ddd-oi.store/api/v1/s3/images/경복궁.jpeg\",\n    \"latitude\": 37.5808473,\n    \"longitude\": 126.9768441\n  },\n  \"message\": \"스팟 조회 성공\"\n}"))))
	CustomApiResponse<ContentsSpotResponse> getSpot(Long spotId);

	@Operation(summary = "스팟 수정", description = "스팟 정보를 수정합니다.", requestBody = @RequestBody(content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "ContentsSpotRequest 예시", value = "{\n  \"spotName\": \"수정된 스팟 이름\",\n  \"address\": \"수정된 주소\",\n  \"spotDescription\": \"수정된 설명\",\n  \"spotImage\": \"updated-image.jpg\",\n  \"latitude\": 37.123456,\n  \"longitude\": 126.654321\n}"))), responses = @ApiResponse(responseCode = "200", description = "스팟 수정 성공", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "성공 응답 예시", value = "{\n  \"statusCode\": 200,\n  \"resultType\": \"SUCCESS\",\n  \"data\": {\n    \"id\": 3,\n    \"spotName\": \"수정된 스팟 이름\",\n    \"address\": \"수정된 주소\",\n    \"spotDescription\": \"수정된 설명\",\n    \"spotImage\": \"https://ddd-oi.store/api/v1/s3/images/updated-image.jpg\",\n    \"latitude\": 37.123456,\n    \"longitude\": 126.654321\n  },\n  \"message\": \"스팟 수정 성공\"\n}"))))
	CustomApiResponse<ContentsSpotResponse> updateSpot(Long spotId, ContentsSpotRequest request);

	@Operation(summary = "스팟 삭제", description = "스팟 ID를 통해 스팟을 삭제합니다.", responses = @ApiResponse(responseCode = "200", description = "스팟 삭제 성공", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "성공 응답 예시", value = "{\n  \"statusCode\": 200,\n  \"resultType\": \"SUCCESS\",\n  \"data\": null,\n  \"message\": \"스팟 삭제 성공\"\n}"))))
	CustomApiResponse<Void> deleteSpot(Long spotId);

	@Operation(summary = "스팟 목록 조회", description = "컨텐츠 ID를 통해 스팟 목록을 조회합니다.", responses = @ApiResponse(responseCode = "200", description = "스팟 목록 조회 성공", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "성공 응답 예시", value = "{\n  \"statusCode\": 200,\n  \"resultType\": \"SUCCESS\",\n  \"data\": [\n    {\n      \"id\": 3,\n      \"spotName\": \"경복궁\",\n      \"address\": \"서울특별시 종로구 사직로 161 경복궁\",\n      \"spotDescription\": \"서울의 대표적인 관광 명소입니다.\",\n      \"spotImage\": \"https://ddd-oi.store/api/v1/s3/images/경복궁.jpeg\",\n      \"latitude\": 37.5808473,\n      \"longitude\": 126.9768441\n    }\n  ],\n  \"message\": \"장소 목록 조회 성공\"\n}"))))
	CustomApiResponse<List<ContentsSpotResponse>> getSpots(Long contentsId);
}
