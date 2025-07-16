package com.ddd.oi.auth.controller;

import com.ddd.oi.auth.dto.AuthResponseDTO;
import com.ddd.oi.auth.service.AuthService;
import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.CustomApiResponse;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.user.domain.ProviderInfo;
import com.ddd.oi.user.dto.UserConverter;
import com.ddd.oi.user.dto.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login/{provider}")
    public CustomApiResponse<UserResponseDTO> login(@PathVariable("provider") String provider, @RequestParam("code") String accessCode, HttpServletResponse httpServletResponse) {
        ProviderInfo providerInfo = ProviderInfo.of(provider);
        AuthResponseDTO responseDTO = authService.oAuthLogin(providerInfo, accessCode, httpServletResponse);

        return CustomApiResponse.success(
                UserConverter.toJoinResultDTO(responseDTO.user(), responseDTO.accessToken(), responseDTO.refreshToken()),
                200,
                "로그인 성공"
        );
    }

    @PostMapping("/reissue")
    public CustomApiResponse<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new OiException(ErrorCode.HEADER_REFRESH_TOKEN_NOT_EXISTS);
        }

        String refreshToken = bearerToken.substring(7);

        AuthResponseDTO dto = authService.reissueAccessToken(refreshToken, response);

        return CustomApiResponse.success(dto, 200, "토큰 재발급 성공");
    }

}
