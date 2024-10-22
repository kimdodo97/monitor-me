package com.monitorme.backend.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GoogleUseInfo {
    @JsonProperty("social_id")
    private String socialId;
    private String email;

    @JsonProperty("verified_email")
    private String verifiedEmail;

    private String picture;

    @Builder
    public GoogleUseInfo(String socialId, String email, String verifiedEmail, String picture) {
        this.socialId = socialId;
        this.email = email;
        this.verifiedEmail = verifiedEmail;
        this.picture = picture;
    }
}
