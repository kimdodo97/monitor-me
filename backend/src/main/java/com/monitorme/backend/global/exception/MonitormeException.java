package com.monitorme.backend.global.exception;

import java.util.HashMap;
import java.util.Map;

public abstract class MonitormeException extends RuntimeException{
    public MonitormeException(String message) {
        super(message);
    }

    public MonitormeException(String message, Throwable cause){
        super(message,cause);
    }

    public abstract int getStatusCode();
}
