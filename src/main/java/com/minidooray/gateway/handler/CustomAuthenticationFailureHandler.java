package com.minidooray.gateway.handler;

import com.minidooray.gateway.dto.ErrorResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ErrorResponse errorResponse = new ErrorResponse(401, "로그인에 실패했습니다.");

        request.setAttribute("errorMessage", errorResponse);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/error");
        dispatcher.forward(request, response);
    }
}
