package com.monitorme.backend.auth.exception;

import com.monitorme.backend.global.exception.MonitormeException;

public class GoogleUserInfoException extends MonitormeException {
    private static String MESSAGE = "구글 사용자 정보 가져오기 실패";
    public GoogleUserInfoException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
