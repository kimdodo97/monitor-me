package com.monitorme.backend.auth.exception;

import com.monitorme.backend.global.exception.MonitormeException;

public class GoogleAuthTokenException extends MonitormeException {

    private static String MESSAGE = "구글 토큰 발급이 실패했습니다.";

    public GoogleAuthTokenException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401 ;
    }
}
