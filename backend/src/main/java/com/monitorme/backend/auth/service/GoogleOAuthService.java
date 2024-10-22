package com.monitorme.backend.auth.service;

import com.monitorme.backend.auth.dto.GoogleAuthRequest;
import com.monitorme.backend.auth.dto.GoogleAuthResponse;
import com.monitorme.backend.auth.dto.GoogleUseInfo;
import com.monitorme.backend.auth.exception.GoogleAuthTokenException;
import com.monitorme.backend.auth.exception.GoogleOAuthHttpException;
import com.monitorme.backend.auth.exception.GoogleUserInfoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleOAuthService {
    private final RestClient restClient;

    private final String URI = "https://oauth2.googleapis.com/token";
    private final String USERINFO_URL="https://www.googleapis.com/oauth2/v1/userinfo";
    private final String GRANT_TYPE = "authorization_code";

    @Value("${oauth2.redirect-uri}")
    private String REDIRECT_URI;

    @Value("${oauth2.client-id}")
    private String CLIENT_ID;

    @Value("${oauth2.client-secret}")
    private String CLIENT_SECRET;

    public GoogleOAuthService() {
        this.restClient = RestClient.create();
    }

    public GoogleAuthResponse getOAuthToken(String code) throws HttpClientErrorException{
        GoogleAuthRequest body = GoogleAuthRequest.builder()
                .grantType(GRANT_TYPE)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .redirectUri(REDIRECT_URI)
                .code(code)  // code 추가
                .build();

        ResponseEntity<GoogleAuthResponse> responseEntity = restClient.post()
                .uri(URI)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(body)  // bodyValue 사용
                .retrieve()
                .toEntity(GoogleAuthResponse.class);

        if (responseEntity.getStatusCode().value() != 200){
            throw new GoogleAuthTokenException();
        }

        return responseEntity.getBody();
    }

    public void getUserInfo(String accessToken) throws HttpClientErrorException{
        ResponseEntity<GoogleUseInfo> responseEntity = restClient.get()
                .uri(USERINFO_URL)
                .header("Authorization", "Bearer" + accessToken)
                .retrieve()
                .toEntity(GoogleUseInfo.class);

        if (responseEntity.getStatusCode().value() != 200){
            throw new GoogleUserInfoException();
        }
    }
}
