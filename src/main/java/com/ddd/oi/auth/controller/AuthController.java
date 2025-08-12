package com.ddd.oi.auth.controller;

import com.ddd.oi.auth.dto.AuthResponse;
import com.ddd.oi.auth.dto.OAuthAccessTokenRequest;
import com.ddd.oi.auth.service.AuthService;
import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.CustomApiResponse;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.user.domain.ProviderInfo;
import com.ddd.oi.user.domain.User;
import com.ddd.oi.user.dto.UserConverter;
import com.ddd.oi.user.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "소셜 로그인 컨트롤러", description = "소셜 로그인 관련 API입니다.")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login/{provider}")
    @Operation(summary = "로그인", description = "로그인 API [provider : kakao,naver,google]")
    public CustomApiResponse<UserResponseDTO> login(
            @PathVariable("provider") String provider,
            @RequestBody OAuthAccessTokenRequest request,
            HttpServletResponse response
    ) {
        ProviderInfo providerInfo = ProviderInfo.of(provider);
        String oauthAccessToken = request.oauthAccessToken();
        AuthResponse authDTO = authService.oAuthLogin(providerInfo, oauthAccessToken, response);

        return CustomApiResponse.success(
                UserConverter.toJoinResultDTO(authDTO.user(), authDTO.accessToken(), authDTO.refreshToken(), authDTO.oauthAccessToken()
                ),
                200,
                "카카오 로그인 성공"
        );
    }


    @PostMapping("/reissue")
    @Operation(summary = "리프레시 토큰 재발급", description = "리프레시 토큰 재발급 API")
    public CustomApiResponse<?> reissue(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new OiException(ErrorCode.HEADER_REFRESH_TOKEN_NOT_EXISTS);
        }

        String refreshToken = bearerToken.substring(7);

        AuthResponse dto = authService.reissueAccessToken(refreshToken, response);

        return CustomApiResponse.success(dto, 200, "토큰 재발급 성공");
    }
    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃 API")
    public CustomApiResponse<?> logout(
            @AuthenticationPrincipal User user,
            @RequestParam("provider") ProviderInfo provider,
            @RequestHeader("Oauth-Authorization") String oauthAccessToken
    ) {
        Boolean result = authService.logout(provider, oauthAccessToken, user.getEmail());
        return CustomApiResponse.success(result,200,"로그아웃 성공");
    }
}
