package com.monitorme.backend.auth.service;

import com.monitorme.backend.auth.dto.GoogleAuthResponse;
import com.monitorme.backend.auth.dto.GoogleUseInfo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;

@RestClientTest(value={GoogleOAuthService.class})
class GoogleOAuthServiceTest {
    @Autowired
    private GoogleOAuthService googleOAuthService;

    @Autowired
    private MockRestServiceServer mockServer;

    @BeforeEach
    void clear() {
        mockServer.reset();
    }

    @Test
    @DisplayName("구글 소셜 Access Token 획득 성공 테스트")
    void successGetToken() throws Exception {
        // given
        String code = "testCode1";
        GoogleAuthResponse expectedResponse = GoogleAuthResponse.builder()
                .accessToken("testAccessToken")
                .idToken("1234")
                .expiresIn(1234)
                .scope("email")
                .tokenType("Bearer")
                .build();

        // when
        mockServer.expect(requestTo("https://oauth2.googleapis.com/token"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(MockRestResponseCreators.withSuccess(
                        "{\"access_token\":\"testAccessToken\",\"id_token\":\"1234\",\"expires_in\":1234,\"scope\":\"email\",\"token_type\":\"Bearer\"}",
                        MediaType.APPLICATION_JSON));

        GoogleAuthResponse actualResponse = googleOAuthService.getOAuthToken(code);

        // then
        assertAll(
                () -> mockServer.verify(),
                () -> assertThat(actualResponse.getAccessToken()).isEqualTo(expectedResponse.getAccessToken()),
                () -> assertThat(actualResponse.getIdToken()).isEqualTo(expectedResponse.getIdToken()),
                () -> assertThat(actualResponse.getExpiresIn()).isEqualTo(expectedResponse.getExpiresIn()),
                () -> assertThat(actualResponse.getScope()).isEqualTo(expectedResponse.getScope()),
                () -> assertThat(actualResponse.getTokenType()).isEqualTo(expectedResponse.getTokenType())
        );
    }

    @Test
    @DisplayName("구글 소셜 사용자 정보 획득 성공 테스트")
    void successGetUserInfo() throws Exception {
        // given
        String accessToken = "1234";
        GoogleUseInfo expectedResponse = GoogleUseInfo.builder()
                .email("test@test.com")
                .verifiedEmail("true")
                .picture("picture")
                .socialId("1234")
                .build();

        // when
        mockServer.expect(requestTo("https://www.googleapis.com/oauth2/v1/userinfo"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("Authorization", "Bearer 1234")) // Authorization 헤더 검증
                .andRespond(MockRestResponseCreators.withSuccess(
                        "{\"email\":\"test@test.com\",\"verified_email\":true,\"picture\":\"picture\",\"social_id\":\"1234\"}",
                        MediaType.APPLICATION_JSON));

        GoogleUseInfo actualResponse = googleOAuthService.getUserInfo(accessToken);

        // then
        assertAll(
                () -> mockServer.verify(),
                () -> assertThat(actualResponse.getEmail()).isEqualTo(expectedResponse.getEmail()),
                () -> assertThat(actualResponse.getPicture()).isEqualTo(expectedResponse.getPicture()),
                () -> assertThat(actualResponse.getSocialId()).isEqualTo(expectedResponse.getSocialId()),
                () -> assertThat(actualResponse.getVerifiedEmail()).isEqualTo(expectedResponse.getVerifiedEmail())
        );
    }

}