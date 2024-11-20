package com.sporty.backend.global.exception;

import java.util.HashMap;
import java.util.Map;

public abstract class SportyException extends RuntimeException{
    public SportyException(String message) {
        super(message);
    }

    public SportyException(String message, Throwable cause){
        super(message,cause);
    }

    public abstract int getStatusCode();
}
