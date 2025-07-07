package com.ddd.oi.auth.dto;

import com.ddd.oi.user.domain.User;

public record AuthResponseDTO(User user, String accessToken,String refreshToken) {
}
