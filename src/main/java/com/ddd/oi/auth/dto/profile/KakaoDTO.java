package com.ddd.oi.auth.dto.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class KakaoDTO {

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OAuthToken {
        private String access_token;
        private String token_type;
        private String refresh_token;
        private int expires_in;
        private int refresh_token_expires_in;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KakaoProfile {
        private String id;
        private String email;
        private String nickname;
//        private String profileImage;

        public static KakaoProfile fromJson(com.fasterxml.jackson.databind.JsonNode root) {
            var kakaoAccount = root.path("kakao_account");
            var profileNode = kakaoAccount.path("profile");

            return KakaoProfile.builder()
                    .id(root.path("id").asText())
                    .email(kakaoAccount.path("email").asText(null))
                    .nickname(profileNode.path("nickname").asText(null))
//                    .profileImage(profileNode.path("profile_image_url").asText(null))
                    .build();
        }
    }
}
