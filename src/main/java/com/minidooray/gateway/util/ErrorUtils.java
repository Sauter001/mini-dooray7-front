package com.minidooray.gateway.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minidooray.gateway.dto.ErrorResponse;
import com.minidooray.gateway.exception.AccountException;

public class ErrorUtils {
    public static void handleError(Exception e, ObjectMapper objectMapper) {
        String errorMessage = e.getMessage();
        String errorJson = errorMessage.substring(errorMessage.indexOf('{'), errorMessage.lastIndexOf('}') + 1);

        try {
            ErrorResponse errorResponse = objectMapper.readValue(errorJson, ErrorResponse.class);
            throw new AccountException(errorResponse.getCode(), errorResponse);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
