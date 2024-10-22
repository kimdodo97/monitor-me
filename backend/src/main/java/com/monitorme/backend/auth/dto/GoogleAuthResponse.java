package com.monitorme.backend.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GoogleAuthResponse {
    @JsonProperty("access_token")
    private String accessToken = "";

    @JsonProperty("expires_in")
    private Integer expiresIn;

    private String scope;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("id_token")
    private String idToken;

    @Builder
    public GoogleAuthResponse(String accessToken, Integer expiresIn, String scope, String tokenType, String idToken) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.scope = scope;
        this.tokenType = tokenType;
        this.idToken = idToken;
    }
}
