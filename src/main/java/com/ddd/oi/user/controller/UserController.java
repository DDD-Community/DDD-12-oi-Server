package com.ddd.oi.user.controller;

import com.ddd.oi.common.response.CustomApiResponse;
import com.ddd.oi.user.controller.docs.UserControllerDocs;
import com.ddd.oi.user.domain.User;
import com.ddd.oi.user.dto.request.UpdateNicknameRequest;
import com.ddd.oi.user.dto.response.ShowUserResponse;
import com.ddd.oi.user.dto.response.UpdateNicknameResponse;
import com.ddd.oi.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController implements UserControllerDocs {

	private final UserService userService;

	@DeleteMapping
	@Override
	public CustomApiResponse<?> deleteUser(
		@AuthenticationPrincipal User user,
		@RequestHeader("Oauth-Authorization") String oauthAccessToken
	) {
		Boolean result = userService.deleteUser(user, oauthAccessToken);
		return CustomApiResponse.success(result, 200, "유저 탈퇴 성공");
	}

	@PatchMapping
	@Override
	public CustomApiResponse<UpdateNicknameResponse> updateUserNickname(
		@AuthenticationPrincipal User user,
		@Valid UpdateNicknameRequest request
	) {
		UpdateNicknameResponse result = userService.updateNickname(user, request);
		return CustomApiResponse.success(result, 200, "유저 닉네임 수정 성공");
	}

	@GetMapping
	@Override
	public CustomApiResponse<ShowUserResponse> showUserAndSystemInfo(
		@AuthenticationPrincipal User user
	) {
		ShowUserResponse result = userService.showUserAndSystemInfo(user.getId());
		return CustomApiResponse.success(result, 200, "유저 정보 조회/시스템 정보 조회 성공");
	}

}
