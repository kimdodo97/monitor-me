package com.sporty.backend.auth.exception;

import com.sporty.backend.global.exception.SportyException;

public class GoogleOAuthHttpException extends SportyException {
    private static String MESSAGE = "구글 OAuth 인증 서버 통신 실패";
    public GoogleOAuthHttpException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
