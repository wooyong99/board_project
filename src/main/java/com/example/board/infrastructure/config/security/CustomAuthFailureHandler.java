package com.example.board.infrastructure.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.util.UriUtils;

public class CustomAuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException, ServletException {

        String failUrl = "/members/loginForm";

        String errorMessage = exception.getMessage();

        String encodeMessage = UriUtils.encode(errorMessage, "UTF-8");

        response.sendRedirect(failUrl + "?errorMessage=" + encodeMessage);
    }


}
