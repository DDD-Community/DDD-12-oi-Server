package com.ddd.oi.user.controller;

import com.ddd.oi.common.response.CustomApiResponse;
import com.ddd.oi.user.domain.User;
import com.ddd.oi.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public CustomApiResponse<?> markUserAsDormant(
            @AuthenticationPrincipal User user,
            @RequestHeader("Oauth-Authorization") String oauthAccessToken
    ) {
        Boolean result = userService.deleteUser(user, oauthAccessToken);
        return CustomApiResponse.success(result, 200, "유저 탈퇴 성공");
    }


}
