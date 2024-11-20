package com.sporty.backend.auth.service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Service
public class GoogleOAuthService {
    private final RestClient restClient;
    private static String URI = "https://oauth2.googleapis.com/token";
    private static String USERINFO_URL="https://www.googleapis.com/oauth2/v1/userinfo";
    private static String GRANT_TYPE = "authorization_code";

    @Value("${oauth2.redirect-uri}")
    private String REDIRECT_URI;

    @Value("${oauth2.client-id}")
    private String CLIENT_ID;

    @Value("${oauth2.client-secret}")
    private String CLIENT_SECRET;

    public GoogleOAuthService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public com.sporty.backend.auth.dto.GoogleAuthResponse getOAuthToken(String code) throws HttpClientErrorException{
        com.sporty.backend.auth.dto.GoogleAuthRequest body = com.sporty.backend.auth.dto.GoogleAuthRequest.builder()
                .grantType(GRANT_TYPE)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .redirectUri(REDIRECT_URI)
                .code(code)  // code 추가
                .build();

        ResponseEntity<com.sporty.backend.auth.dto.GoogleAuthResponse> responseEntity = restClient.post()
                .uri(URI)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(body)  // bodyValue 사용
                .retrieve()
                .toEntity(com.sporty.backend.auth.dto.GoogleAuthResponse.class);

        if (responseEntity.getStatusCode().value() != 200){
            throw new com.sporty.backend.auth.exception.GoogleAuthTokenException();
        }

        return responseEntity.getBody();
    }

    public com.sporty.backend.auth.dto.GoogleUseInfo getUserInfo(String accessToken) throws HttpClientErrorException{
        ResponseEntity<com.sporty.backend.auth.dto.GoogleUseInfo> responseEntity = restClient.get()
                .uri(USERINFO_URL)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .toEntity(com.sporty.backend.auth.dto.GoogleUseInfo.class);

        if (responseEntity.getStatusCode().value() != 200){
            throw new com.sporty.backend.auth.exception.GoogleUserInfoException();
        }

        return responseEntity.getBody();
    }
}
