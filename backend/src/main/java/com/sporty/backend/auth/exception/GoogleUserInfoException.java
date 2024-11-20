package com.sporty.backend.auth.exception;

import com.sporty.backend.global.exception.SportyException;

public class GoogleUserInfoException extends SportyException {
    private static String MESSAGE = "구글 사용자 정보 가져오기 실패";
    public GoogleUserInfoException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
