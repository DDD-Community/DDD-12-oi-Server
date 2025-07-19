package com.ddd.oi.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

public class NaverDTO {
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OAuthToken {
        private String access_token;
        private String refresh_token;
        private String token_type;
        private String expires_in;
    }
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NaverProfile {
        private String message;
        private Response response;

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Response {
            private String id;
            private String email;
            private String name;
            private String nickname;
            private String profile_image;
        }
    }

}
