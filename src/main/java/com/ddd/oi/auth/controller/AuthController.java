package com.ddd.oi.auth.controller;

import com.ddd.oi.auth.dto.AuthResponseDTO;
import com.ddd.oi.auth.service.AuthService;
import com.ddd.oi.common.response.CustomApiResponse;
import com.ddd.oi.user.domain.User;
import com.ddd.oi.user.dto.UserConverter;
import com.ddd.oi.user.dto.UserResponseDTO.JoinResultDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login/kakao")
    public CustomApiResponse<JoinResultDTO> kakaoLogin(
            @RequestParam("code") String accessCode,
            HttpServletResponse httpServletResponse
    ) {
        AuthResponseDTO responseDTO = authService.oAuthLogin(accessCode, httpServletResponse);
        return CustomApiResponse.success(
                UserConverter.toJoinResultDTO(responseDTO.user(), responseDTO.accessToken(),
                        responseDTO.refreshToken()),
                200,
                "로그인성공"
        );
    }
    @PostMapping("/reissue")
    public CustomApiResponse<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        String bearerToken = request.getHeader("Authorization");

        String refreshToken = bearerToken.substring(7);
        AuthResponseDTO dto = authService.reissueAccessToken(refreshToken,response);
        return CustomApiResponse.success(dto,200,"토큰 재발급 성공");
    }


}
