package com.sporty.backend.auth;

import com.sporty.backend.auth.service.GoogleOAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final GoogleOAuthService googleOAuthService;

    @GetMapping("api/oauth2/callback/google")
    public ResponseEntity<String> getGoogleLogin(@RequestParam String code){
        com.sporty.backend.auth.dto.GoogleAuthResponse googleAuthResponse = googleOAuthService.getOAuthToken(code);
        com.sporty.backend.auth.dto.GoogleUseInfo userInfo = googleOAuthService.getUserInfo(googleAuthResponse.getAccessToken());

        return ResponseEntity.ok(userInfo.getEmail());
    }
}
