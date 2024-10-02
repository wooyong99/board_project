package com.example.board.api.controller.member;

import com.example.board.api.controller.member.request.LoginRequest;
import com.example.board.api.controller.member.response.LoginResponse;
import com.example.board.application.usecase.member.LoginMemberUseCase;
import com.example.board.application.usecase.member.LogoutMemberUseCase;
import com.example.board.application.usecase.member.ReissueMemberUseCase;
import com.example.board.application.usecase.member.dto.LoginServiceRequest;
import com.example.board.application.usecase.member.dto.LoginServiceResponse;
import com.example.board.application.usecase.member.dto.LogoutMemberServiceRequest;
import com.example.board.application.usecase.member.dto.ReissueMemberServiceRequest;
import com.example.board.application.usecase.member.dto.ReissueMemberServiceResponse;
import com.example.board.infrastructure.config.security.TokenProvider;
import com.example.board.infrastructure.utils.HttpServletUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final HttpServletUtils servletUtils;
    private final TokenProvider tokenProvider;
    private final ReissueMemberUseCase reissueMemberUseCase;
    private final LoginMemberUseCase loginMemberUseCase;
    private final LogoutMemberUseCase logoutMemberUseCase;
    private String refreshTokenExpiration;

    @Autowired
    public AuthController(HttpServletUtils servletUtils, TokenProvider tokenProvider,
        Environment env,
        ReissueMemberUseCase reissueMemberUseCase,
        LoginMemberUseCase loginMemberUseCase, LogoutMemberUseCase logoutMemberUseCase) {
        this.servletUtils = servletUtils;
        this.tokenProvider = tokenProvider;
        this.refreshTokenExpiration = env.getProperty("jwt.token-validity-in-seconds");
        this.reissueMemberUseCase = reissueMemberUseCase;
        this.loginMemberUseCase = loginMemberUseCase;
        this.logoutMemberUseCase = logoutMemberUseCase;
    }

    // 액세스 토큰 재발급 ( 회원 유저만 접근 가능 )
    @GetMapping("/reissue")
    public ResponseEntity reissue(HttpServletRequest request, HttpServletResponse response) {
        ReissueMemberServiceRequest serviceDto = new ReissueMemberServiceRequest(request, response);
        ReissueMemberServiceResponse reissue = reissueMemberUseCase.reissue(serviceDto);
        return ResponseEntity.ok(reissue);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest, HttpServletRequest request,
        HttpServletResponse response) {
        LoginServiceRequest serviceRequestDto = new LoginServiceRequest(loginRequest.getEmail(),
            loginRequest.getPassword());
        LoginServiceResponse serviceResponseDto = loginMemberUseCase.login(
            serviceRequestDto);
        String refreshToken = servletUtils.getCookie(request, "RefreshToken")
            .map(Cookie::getValue)
            .orElse(null);
        if (!tokenProvider.validateToken(refreshToken)) {
            servletUtils.addCookie(response, "RefreshToken", serviceResponseDto.getRefreshToken(),
                Integer.parseInt(refreshTokenExpiration));
        }
        return ResponseEntity.ok(
            new LoginResponse(serviceResponseDto.getEmail(), serviceResponseDto.getNickname(),
                serviceResponseDto.getAccessToken(), serviceResponseDto.isBlocked(),
                serviceResponseDto.isAdmin()));
    }

    // 로그아웃
    @PostMapping("/logout")
//    @AuthorizationRequired({RoleEnum.EXERCISE_ADMIN, RoleEnum.USER})
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {
        LogoutMemberServiceRequest serviceDto = new LogoutMemberServiceRequest(request, response);
        logoutMemberUseCase.logout(serviceDto);
        return ResponseEntity.ok().build();
    }

}
