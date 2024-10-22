package com.monitorme.backend.auth.exception;

import com.monitorme.backend.global.exception.MonitormeException;

public class GoogleOAuthHttpException extends MonitormeException {
    private static String MESSAGE = "구글 OAuth 인증 서버 통신 실패";
    public GoogleOAuthHttpException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
