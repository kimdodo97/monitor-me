package com.sporty.backend.auth.exception;

import com.sporty.backend.global.exception.SportyException;

public class GoogleAuthTokenException extends SportyException {

    private static String MESSAGE = "구글 토큰 발급이 실패했습니다.";

    public GoogleAuthTokenException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401 ;
    }
}
