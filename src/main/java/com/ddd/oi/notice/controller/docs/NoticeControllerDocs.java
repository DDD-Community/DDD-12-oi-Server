package com.ddd.oi.notice.controller.docs;

import com.ddd.oi.notice.dto.NoticeCreateRequest;
import com.ddd.oi.notice.dto.NoticeResponse;
import com.ddd.oi.notice.dto.NoticeUpdateRequest;
import com.ddd.oi.common.response.CustomApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Tag(name = "공지사항 컨트롤러", description = "공지사항 관련 API입니다.")
public interface NoticeControllerDocs {

	@Operation(summary = "공지사항 생성", description = "공지사항을 생성합니다.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "NoticeCreateRequest 예시", value = "{\n  \"title\": \"공지사항 제목\",\n  \"content\": \"공지사항 내용\"\n}"))), responses = @ApiResponse(responseCode = "200", description = "공지사항 생성 성공", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "성공 응답 예시", value = "{\n  \"statusCode\": 200,\n  \"resultType\": \"SUCCESS\",\n  \"data\": {\n    \"id\": 1,\n    \"title\": \"공지사항 제목\",\n    \"content\": \"공지사항 내용\",\n    \"updatedAt\": \"2023-10-01T12:00:00\"\n  },\n  \"message\": \"공지사항 생성 성공\"\n}"))))
	CustomApiResponse<NoticeResponse> createNotice(@RequestBody @Valid NoticeCreateRequest request);

	@Operation(summary = "공지사항 수정", description = "공지사항을 수정합니다.", parameters = @Parameter(name = "id", description = "공지사항 ID", example = "1"), requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "NoticeUpdateRequest 예시", value = "{\n  \"title\": \"수정된 제목\",\n  \"content\": \"수정된 내용\"\n}"))), responses = @ApiResponse(responseCode = "200", description = "공지사항 수정 성공", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "성공 응답 예시", value = "{\n  \"statusCode\": 200,\n  \"resultType\": \"SUCCESS\",\n  \"data\": {\n    \"id\": 1,\n    \"title\": \"수정된 제목\",\n    \"content\": \"수정된 내용\",\n    \"updatedAt\": \"2023-10-01T12:00:00\"\n  },\n  \"message\": \"공지사항 수정 성공\"\n}"))))
	CustomApiResponse<NoticeResponse> updateNotice(@PathVariable Long id,
		@RequestBody @Valid NoticeUpdateRequest request);

	@Operation(summary = "공지사항 목록 조회", description = "공지사항 목록을 페이지네이션으로 조회합니다. \n  정렬기준은 updatedAt을 넣어주세요.", parameters = {
		@Parameter(name = "page", description = "페이지 번호 (0부터 시작)", example = "0"),
		@Parameter(name = "size", description = "페이지 크기", example = "8"),
		@Parameter(name = "sort", description = "정렬 기준", example = "updatedAt")}, responses = @ApiResponse(responseCode = "200", description = "공지사항 목록 조회 성공", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "성공 응답 예시", value = "{\n  \"statusCode\": 200,\n  \"resultType\": \"SUCCESS\",\n  \"data\": {\n    \"content\": [\n      {\n        \"id\": 1,\n        \"title\": \"공지사항 제목\",\n        \"content\": \"공지사항 내용\",\n        \"updatedAt\": \"2023-10-01T12:00:00\"\n      }\n    ],\n    \"pageable\": {\n      \"pageNumber\": 0,\n      \"pageSize\": 10\n    }\n  },\n  \"message\": \"공지사항 목록 조회 성공\"\n}"))))
	CustomApiResponse<Page<NoticeResponse>> getNotices(Pageable pageable);

	@Operation(summary = "공지사항 조회", description = "공지사항 ID를 통해 공지사항을 조회합니다.", parameters = @Parameter(name = "id", description = "공지사항 ID", example = "1"), responses = @ApiResponse(responseCode = "200", description = "공지사항 조회 성공", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "성공 응답 예시", value = "{\n  \"statusCode\": 200,\n  \"resultType\": \"SUCCESS\",\n  \"data\": {\n    \"id\": 1,\n    \"title\": \"공지사항 제목\",\n    \"content\": \"공지사항 내용\",\n    \"updatedAt\": \"2023-10-01T12:00:00\"\n  },\n  \"message\": \"공지사항 조회 성공\"\n}"))))
	CustomApiResponse<NoticeResponse> getNotice(@PathVariable Long id);

	@Operation(summary = "공지사항 삭제", description = "공지사항 ID를 통해 공지사항을 삭제합니다.", parameters = @Parameter(name = "id", description = "공지사항 ID", example = "1"), responses = @ApiResponse(responseCode = "200", description = "공지사항 삭제 성공", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "성공 응답 예시", value = "{\n  \"statusCode\": 200,\n  \"resultType\": \"SUCCESS\",\n  \"data\": 1,\n  \"message\": \"공지사항 삭제 성공\"\n}"))))
	CustomApiResponse<Long> deleteNotice(@PathVariable Long id);
}
