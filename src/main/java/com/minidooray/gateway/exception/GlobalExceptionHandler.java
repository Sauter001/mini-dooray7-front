package com.minidooray.gateway.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccountException.class)
    public String accountExceptionHandler(AccountException e, Model model) {
        model.addAttribute("errorMessage", e.getErrorMessage());
        return "error";
    }
}
