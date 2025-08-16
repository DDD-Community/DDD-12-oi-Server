package com.ddd.oi.auth.controller;


import com.ddd.oi.auth.dto.AuthResponse;
import com.ddd.oi.auth.dto.OAuthAccessTokenRequest;
import com.ddd.oi.auth.service.AuthService;
import com.ddd.oi.user.domain.ProviderInfo;
import com.ddd.oi.user.domain.RoleType;
import com.ddd.oi.user.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import jakarta.servlet.http.HttpServletResponse;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
public class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    private User domainUser(String email) {
        return User.builder()
                .email(email)
                .nickname("tester")
                .role(RoleType.USER)
                .providerInfo(ProviderInfo.KAKAO)
                .build();
    }
    @Test
    @DisplayName("로그인 성공")
    void 로그인_성공_kakao() throws Exception {
        // given
        String oauthAccess = "oauthToken";
        String email = "user@example.com";
        User user = domainUser(email);

        AuthResponse stub = new AuthResponse(user, "ACCESSTOKEN", "REFRESHTOKEN", oauthAccess);

        given(authService.oAuthLogin(eq(ProviderInfo.KAKAO), eq(oauthAccess), any(HttpServletResponse.class)))
                .willReturn(stub);

        OAuthAccessTokenRequest body = new OAuthAccessTokenRequest(oauthAccess);

        // when & then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/auth/login/{provider}", "kakao")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(body))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value(email))
                .andExpect(jsonPath("$.data.accessToken").value("ACCESSTOKEN"))
                .andExpect(jsonPath("$.data.refreshToken").value("REFRESHTOKEN"))
                .andExpect(jsonPath("$.data.oauthAccessToken").value(oauthAccess))
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("로그인 성공"));


        verify(authService, times(1))
                .oAuthLogin(eq(ProviderInfo.KAKAO), eq(oauthAccess), any(HttpServletResponse.class));
    }
}
