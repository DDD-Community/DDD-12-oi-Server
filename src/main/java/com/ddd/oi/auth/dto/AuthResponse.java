package com.ddd.oi.auth.dto;

import com.ddd.oi.user.domain.User;

public record AuthResponse(User user, String accessToken, String refreshToken, String oauthAccessToken) {
}
