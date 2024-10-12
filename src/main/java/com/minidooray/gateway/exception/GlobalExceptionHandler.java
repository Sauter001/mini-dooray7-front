package com.minidooray.gateway.exception;

import com.minidooray.gateway.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccountException.class)
    public ResponseEntity<ErrorResponse> accountExceptionHandler(AccountException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getErrorMessage());
    }
}
