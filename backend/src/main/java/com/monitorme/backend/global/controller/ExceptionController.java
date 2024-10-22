package com.monitorme.backend.global.controller;

import com.monitorme.backend.global.exception.MonitormeException;
import com.monitorme.backend.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(MonitormeException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> monitormeExceptionHandler(MonitormeException exception){
        int statusCode = exception.getStatusCode();
        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(exception.getStatusCode())
                .body(body);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseBody
    public ErrorResponse invalidExceptionHandler(MethodArgumentNotValidException exception){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("404")
                .message("외부 서버와 통신이 실패했습니다. 다시 시도해주세요")
                .build();

        for (FieldError fieldError : exception.getFieldErrors()){
            errorResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errorResponse;
    }
}
