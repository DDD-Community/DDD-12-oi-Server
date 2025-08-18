package com.ddd.oi.faq.controller.docs;

import com.ddd.oi.common.response.CustomApiResponse;
import com.ddd.oi.common.response.PageResponse;
import com.ddd.oi.faq.dto.FaqCreateRequest;
import com.ddd.oi.faq.dto.FaqResponse;
import com.ddd.oi.faq.dto.FaqUpdateRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "FAQ", description = "FAQ 관련 API")
public interface FaqControllerDocs {

	@Operation(summary = "FAQ 생성", description = "FAQ를 생성합니다.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "FaqCreateRequest 예시", value = "{\n  \"title\": \"FAQ 제목\",\n  \"content\": \"FAQ 내용\"\n}"))), responses = @ApiResponse(responseCode = "200", description = "FAQ 생성 성공", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "성공 응답 예시", value = "{\n  \"statusCode\": 200,\n  \"resultType\": \"SUCCESS\",\n  \"data\": {\n    \"id\": 1,\n    \"title\": \"FAQ 제목\",\n    \"content\": \"FAQ 내용\"\n  },\n  \"message\": \"FAQ 생성 성공\"\n}"))))
	CustomApiResponse<FaqResponse> createFaq(@RequestBody FaqCreateRequest request);

	@Operation(summary = "FAQ 조회", description = "FAQ ID를 통해 FAQ를 조회합니다.", parameters = @Parameter(name = "id", description = "FAQ ID", example = "1"), responses = @ApiResponse(responseCode = "200", description = "FAQ 조회 성공", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "성공 응답 예시", value = "{\n  \"statusCode\": 200,\n  \"resultType\": \"SUCCESS\",\n  \"data\": {\n    \"id\": 1,\n    \"title\": \"FAQ 제목\",\n    \"content\": \"FAQ 내용\"\n  },\n  \"message\": \"FAQ 조회 성공\"\n}"))))
	CustomApiResponse<FaqResponse> getFaq(@PathVariable Long id);

	@Operation(summary = "FAQ 수정", description = "FAQ를 수정합니다.", parameters = @Parameter(name = "id", description = "FAQ ID", example = "1"), requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "FaqUpdateRequest 예시", value = "{\n  \"title\": \"수정된 제목\",\n  \"content\": \"수정된 내용\"\n}"))), responses = @ApiResponse(responseCode = "200", description = "FAQ 수정 성공", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "성공 응답 예시", value = "{\n  \"statusCode\": 200,\n  \"resultType\": \"SUCCESS\",\n  \"data\": {\n    \"id\": 1,\n    \"title\": \"수정된 제목\",\n    \"content\": \"수정된 내용\"\n  },\n  \"message\": \"FAQ 수정 성공\"\n}"))))
	CustomApiResponse<FaqResponse> updateFaq(@PathVariable Long id, @RequestBody FaqUpdateRequest request);

	@Operation(summary = "FAQ 삭제", description = "FAQ ID를 통해 FAQ를 삭제합니다.", parameters = @Parameter(name = "id", description = "FAQ ID", example = "1"), responses = @ApiResponse(responseCode = "200", description = "FAQ 삭제 성공", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "성공 응답 예시", value = "{\n  \"statusCode\": 200,\n  \"resultType\": \"SUCCESS\",\n  \"data\": 1,\n  \"message\": \"FAQ 삭제 성공\"\n}"))))
	CustomApiResponse<Long> deleteFaq(@PathVariable Long id);

	@Operation(summary = "FAQ 목록 조회", description = "FAQ 목록을 페이지네이션으로 조회합니다.", parameters = {
		@Parameter(name = "pageNumber", description = "페이지 번호 (0부터 시작)", example = "0"),
		@Parameter(name = "pageSize", description = "페이지 크기 (기본값: 150)", example = "150")}, responses = @ApiResponse(responseCode = "200", description = "FAQ 목록 조회 성공", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "성공 응답 예시", value = """
		{
		  "statusCode": 200,
		  "resultType": "SUCCESS",
		  "data": {
		    "content": [
		      {
		        "title": "FAQ 제목",
		        "content": "FAQ 내용"
		      }
		    ],
		    "pageNumber": 0,
		    "pageSize": 10,
		    "totalElements": 1,
		    "totalPages": 1,
		    "hasNext": false
		  },
		  "message": "FAQ 목록 조회 성공"
		}
		"""))))
	CustomApiResponse<PageResponse<FaqResponse>> getFaqs(
		@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
		@RequestParam(value = "pageSize", defaultValue = "10") int pageSize);
}
