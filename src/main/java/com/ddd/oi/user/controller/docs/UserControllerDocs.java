package com.ddd.oi.user.controller.docs;

import com.ddd.oi.common.response.CustomApiResponse;
import com.ddd.oi.user.domain.User;
import com.ddd.oi.user.dto.request.UpdateNicknameRequest;
import com.ddd.oi.user.dto.response.ShowUserResponse;
import com.ddd.oi.user.dto.response.UpdateNicknameResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "유저 컨트롤러", description = "유저 관련 API입니다.")
public interface UserControllerDocs {
	@Operation(summary = "유저 탈퇴", description = "유저 탈퇴 API")
	CustomApiResponse<?> deleteUser(
		@AuthenticationPrincipal User user,
		@RequestHeader("Oauth-Authorization") String oauthAccessToken
	);

	@Operation(summary = "유저 닉네임 수정", description = "유저 닉네임 수정 API")
	CustomApiResponse<UpdateNicknameResponse> updateUserNickname(
		@AuthenticationPrincipal User user,
		@Valid UpdateNicknameRequest request
	);

	@Operation(summary = "유저 정보 조회/시스템 정보 조회", description = "유저 정보 조회/시스템 정보 조회 API")
	CustomApiResponse<ShowUserResponse> showUserAndSystemInfo(
		@AuthenticationPrincipal User user
	);
}
