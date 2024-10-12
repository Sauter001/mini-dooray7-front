package com.minidooray.gateway.exception;

import com.minidooray.gateway.dto.ErrorResponse;
import lombok.Getter;

@Getter
public class AccountException extends RuntimeException {
    private final int statusCode;
    private final ErrorResponse errorMessage;

    public AccountException(int statusCode, ErrorResponse errorMessage) {
      this.statusCode = statusCode;
      this.errorMessage = errorMessage;
    }
}
