package com.ddd.oi.contents.controller.docs;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ddd.oi.common.response.CustomApiResponse;
import com.ddd.oi.contents.dto.ContentsCreateRequest;
import com.ddd.oi.contents.dto.ContentsResponse;
import com.ddd.oi.contents.dto.ContentsUpdateRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "컨텐츠", description = "컨텐츠 관련 API")

public interface ContentsControllerDocs {
	@Operation(summary = "컨텐츠 생성", description = "컨텐츠를 생성합니다.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "ContentsCreateRequest 예시", value = "{\n  \"title\": \"경복궁 철쭉 스팟 총정리\",\n  \"displayDescription\": \"종로구·10만원대\",\n  \"cost\": 150000,\n  \"recommendedSchedule\": \"여름휴가에요\",\n  \"duration\": 360,\n  \"contentsTag\": \"DATE\",\n  \"shortTitle\": \"경복궁 철쭉 스팟 총정리\",\n  \"shortDescription\": \"햇살 좋은 날, 고궁 속 산책 어때요?\",\n  \"contentsImage\": \"https://ddd-oi.store/api/v1/s3/images/sample-image.jpg\",\n  \"recommendationScore\": 8.5\n}"))), responses = @ApiResponse(responseCode = "200", description = "컨텐츠 생성 성공", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "성공 응답 예시", value = "{\n  \"statusCode\": 200,\n  \"resultType\": \"SUCCESS\",\n  \"data\": {\n    \"id\": 1,\n    \"title\": \"경복궁 철쭉 스팟 총정리\",\n    \"displayDescription\": \"종로구·10만원대\",\n    \"cost\": 150000,\n    \"recommendedSchedule\": \"여름휴가에요\",\n    \"duration\": 360,\n    \"contentsTag\": \"DATE\",\n    \"shortTitle\": \"경복궁 철쭉 스팟 총정리\",\n    \"shortDescription\": \"햇살 좋은 날, 고궁 속 산책 어때요?\",\n    \"contentsImage\": \"https://ddd-oi.store/api/v1/s3/images/sample-image.jpg\",\n    \"recommendationScore\": 8.5\n  },\n  \"message\": \"컨텐츠 생성 성공\"\n}"))))
	CustomApiResponse<ContentsResponse> createContents(@RequestBody ContentsCreateRequest request);

	@Operation(summary = "컨텐츠 조회", description = "컨텐츠 ID를 통해 컨텐츠를 조회합니다.", parameters = @Parameter(name = "contentsId", description = "컨텐츠 ID", example = "1"), responses = @ApiResponse(responseCode = "200", description = "컨텐츠 조회 성공", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "성공 응답 예시", value = "{\n  \"statusCode\": 200,\n  \"resultType\": \"SUCCESS\",\n  \"data\": {\n    \"id\": 1,\n    \"title\": \"경복궁 철쭉 스팟 총정리\",\n    \"displayDescription\": \"종로구·10만원대\",\n    \"cost\": 150000,\n    \"recommendedSchedule\": \"여름휴가에요\",\n    \"duration\": 360,\n    \"contentsTag\": \"DATE\",\n    \"shortTitle\": \"경복궁 철쭉 스팟 총정리\",\n    \"shortDescription\": \"햇살 좋은 날, 고궁 속 산책 어때요? 고즈넉한 경복궁의 돌길을 따라 바람처럼 걷다 보면 마음까지 가벼워지는 힐링 코스예요. 사진과 추억을 가득 담으며 하루를 시작해보세요. 청량한 나무 그늘 아래 쉬어가며 전각 곳곳을 둘러보면 도심 속에서도 잠시 머리가 맑아져요.\",\n    \"contentsImage\": \"https://ddd-oi.store/api/v1/s3/images/sample-image.jpg\",\n    \"spots\": [\n      {\n        \"id\": 3,\n        \"spotName\": \"경복궁\",\n        \"address\": \"서울특별시 종로구 사직로 161 경복궁\",\n        \"spotDescription\": \"서울의 대표적인 관광 명소입니다.\",\n        \"spotImage\": \"https://ddd-oi.store/api/v1/s3/images/경복궁.jpeg\",\n        \"latitude\": 37.5808473,\n        \"longitude\": 126.9768441\n      }\n    ],\n    \"viewCount\": 1\n  },\n  \"message\": \"컨텐츠 조회 성공\"\n}"))))
	CustomApiResponse<ContentsResponse> getContents(@PathVariable Long contentsId);

	@Operation(summary = "컨텐츠 수정", description = "컨텐츠를 수정합니다.", parameters = @Parameter(name = "contentsId", description = "컨텐츠 ID", example = "1"), requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "ContentsUpdateRequest 예시", value = "{\n  \"title\": \"수정된 제목\",\n  \"displayDescription\": \"수정된 설명\",\n  \"cost\": 15000,\n  \"recommendedSchedule\": \"3박 4일\",\n  \"duration\": 180,\n  \"contentsTag\": \"TRAVEL\",\n  \"shortTitle\": \"수정된 짧은 제목\",\n  \"shortDescription\": \"수정된 짧은 설명\",\n  \"contentsImage\": \"https://ddd-oi.store/api/v1/s3/images/updated-image.jpg\",\n  \"recommendationScore\": 8.5\n}"))), responses = @ApiResponse(responseCode = "200", description = "컨텐츠 수정 성공", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "성공 응답 예시", value = "{\n  \"statusCode\": 200,\n  \"resultType\": \"SUCCESS\",\n  \"data\": {\n    \"id\": 1,\n    \"title\": \"수정된 제목\",\n    \"displayDescription\": \"수정된 설명\",\n    \"cost\": 15000,\n    \"recommendedSchedule\": \"3박 4일\",\n    \"duration\": 180,\n    \"contentsTag\": \"TRAVEL\",\n    \"shortTitle\": \"수정된 짧은 제목\",\n    \"shortDescription\": \"수정된 짧은 설명\",\n    \"contentsImage\": \"https://ddd-oi.store/api/v1/s3/images/updated-image.jpg\",\n    \"recommendationScore\": 8.5\n  },\n  \"message\": \"컨텐츠 수정 성공\"\n}"))))
	CustomApiResponse<ContentsResponse> updateContents(@PathVariable Long contentsId,
		@RequestBody ContentsUpdateRequest request);

	@Operation(summary = "컨텐츠 삭제", description = "컨텐츠 ID를 통해 컨텐츠를 삭제합니다.", parameters = @Parameter(name = "contentsId", description = "컨텐츠 ID", example = "1"), responses = @ApiResponse(responseCode = "200", description = "컨텐츠 삭제 성공", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "성공 응답 예시", value = "{\n  \"statusCode\": 200,\n  \"resultType\": \"SUCCESS\",\n  \"data\": 1,\n  \"message\": \"컨텐츠 삭제 성공\"\n}"))))
	CustomApiResponse<Long> deleteContents(@PathVariable Long contentsId);

	@Operation(summary = "컨텐츠 리스트 조회", description = "컨텐츠 리스트를 조회합니다.", responses = @ApiResponse(responseCode = "200", description = "컨텐츠 리스트 조회 성공", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "성공 응답 예시", value = "{\n  \"statusCode\": 200,\n  \"resultType\": \"SUCCESS\",\n  \"data\": [\n    {\n      \"id\": 1,\n      \"title\": \"경복궁 철쭉 스팟 총정리\",\n      \"displayDescription\": \"종로구·10만원대\",\n      \"cost\": 150000,\n      \"recommendedSchedule\": \"여름휴가에요\",\n      \"duration\": 360,\n      \"contentsTag\": \"DATE\",\n      \"shortTitle\": \"경복궁 철쭉 스팟 총정리\",\n      \"shortDescription\": \"햇살 좋은 날, 고궁 속 산책 어때요?\",\n      \"contentsImage\": \"https://ddd-oi.store/api/v1/s3/images/sample-image.jpg\",\n      \"spots\": [\n        {\n          \"id\": 3,\n          \"spotName\": \"경복궁\",\n          \"address\": \"서울특별시 종로구 사직로 161 경복궁\",\n          \"spotDescription\": \"서울의 대표적인 관광 명소입니다.\",\n          \"spotImage\": \"https://ddd-oi.store/api/v1/s3/images/경복궁.jpeg\",\n          \"latitude\": 37.5808473,\n          \"longitude\": 126.9768441\n        }\n      ],\n      \"viewCount\": 1\n    }\n  ],\n  \"message\": \"컨텐츠 리스트 조회 성공\"\n}"))))
	CustomApiResponse<List<ContentsResponse>> getContentsList();
}
