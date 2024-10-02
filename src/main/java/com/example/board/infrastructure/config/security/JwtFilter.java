package com.example.board.infrastructure.config.security;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.example.board.infrastructure.utils.HttpServletUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String key;

    @Value("${jwt.token-validity-in-seconds}")
    private String accessTokenExpiration;

    @Value("${jwt.token-validity-in-seconds}")
    private String refreshTokenExpiration;

    private final TokenProvider tokenProvider;

    private final HttpServletUtils servletUtils;

    private final SecurityService securityService;

    public JwtFilter(Environment env, TokenProvider tokenProvider, HttpServletUtils servletUtils,
        SecurityService securityService) {
        this.key = env.getProperty("jwt.secret");
        this.accessTokenExpiration = env.getProperty("jwt.token-validity-in-seconds");
        this.refreshTokenExpiration = env.getProperty("jwt.token-validity-in-seconds");
        this.tokenProvider = tokenProvider;
        this.servletUtils = servletUtils;
        this.securityService = securityService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {
        System.out.println("Jwt Filter");
        String accessToken = getAccessToken(request);   // 헤더로부터 Access 토큰 추출
//        String refreshToken = getRefreshToken(request); // 쿠키로부터 Refresh 토큰 추출

        if (tokenProvider.validateToken(accessToken)) { // Access 토큰 유효성 검사
            setAuthentication(accessToken);
        } else {

        }
//        if (tokenProvider.validateToken(accessToken)) { // Access 토큰 유효성 검사
//            setAuthentication(accessToken);
//        } else if (refreshToken != null && tokenProvider.validateToken(
//            refreshToken)) { // 유효하지 않다면 ? (refresh가 유효하는 경우)
//            String subject = tokenProvider.getSubject(refreshToken);
//
//            accessToken = tokenProvider.createToken(subject, false);
//            refreshToken = tokenProvider.createToken(subject, false);
//
//            servletUtils.putHeader(response, AUTHORIZATION, accessToken);
//            servletUtils.addCookie(response, "RefreshToken", refreshToken,
//                Integer.parseInt(refreshTokenExpiration));
//
//            setAuthentication(accessToken);
//        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String accessToken) {
        Authentication authentication = securityService.getAuthentication(
            tokenProvider.getSubject(accessToken));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getAccessToken(HttpServletRequest request) {
        return servletUtils.getHeader(request, AUTHORIZATION).orElse(null);
    }

    private String getRefreshToken(HttpServletRequest request) {
        return servletUtils.getCookie(request, "RefreshToken")
            .map(Cookie::getValue)
            .orElse(null);
    }
}
