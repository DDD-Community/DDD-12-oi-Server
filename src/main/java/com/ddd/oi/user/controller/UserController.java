package com.ddd.oi.user.controller;

import com.ddd.oi.common.response.CustomApiResponse;
import com.ddd.oi.user.domain.User;
import com.ddd.oi.user.dto.request.UpdateNicknameRequest;
import com.ddd.oi.user.dto.response.ShowUserResponse;
import com.ddd.oi.user.dto.response.UpdateNicknameResponse;
import com.ddd.oi.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "유저 컨트롤러", description = "유저 관련 API입니다.")
public class UserController {

    private final UserService userService;

    @DeleteMapping
    @Operation(summary = "유저 탈퇴", description = "유저 탈퇴 API")
    public CustomApiResponse<?> deleteUser(
            @AuthenticationPrincipal User user,
            @RequestHeader("Oauth-Authorization") String oauthAccessToken
    ) {
        Boolean result = userService.deleteUser(user, oauthAccessToken);
        return CustomApiResponse.success(result, 200, "유저 탈퇴 성공");
    }

    @PatchMapping
    @Operation(summary = "유저 닉네임 수정", description = "유저 닉네임 수정 API")
    public CustomApiResponse<UpdateNicknameResponse> updateUserNickname(
            @AuthenticationPrincipal User user,
            @Valid UpdateNicknameRequest request
    ) {
        UpdateNicknameResponse result = userService.updateNickname(user,request);
        return CustomApiResponse.success(result,200,"유저 닉네임 수정 성공");
    }


    @GetMapping
    @Operation(summary = "유저 정보 조회/시스템 정보 조회",description = "유저 정보 조회/시스템 정보 조회 API")
    public CustomApiResponse<ShowUserResponse> showUserAndSystemInfo(
            //TODO 변경예정 @AuthenticationPrincipal User user,
            @RequestHeader("user-no") Long userId
    ) {
        ShowUserResponse result = userService.showUserAndSystemInfo(userId);
        return CustomApiResponse.success(result,200,"유저 정보 조회/시스템 정보 조회 성공");
    }


}
