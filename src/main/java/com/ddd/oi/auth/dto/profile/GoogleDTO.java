package com.ddd.oi.auth.dto.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleDTO {

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OAuthToken {
        private String access_token;
        private String refresh_token;
        private String token_type;
        private String expires_in;
        private String id_token;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GoogleProfile {
        private String id;
        private String email;
        private boolean verified_email;
        private String name;
        private String given_name;
        private String family_name;
        // private String picture;
    }

}

