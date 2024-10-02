package com.example.board.application.usecase.member;

import com.example.board.application.usecase.member.dto.LoginServiceRequest;
import com.example.board.application.usecase.member.dto.LoginServiceResponse;
import com.example.board.exception.BlockMemberException;
import com.example.board.exception.LoginFailedException;
import com.example.board.infrastructure.config.security.CustomDetails;
import com.example.board.infrastructure.config.security.TokenProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginMemberUseCaseImpl implements LoginMemberUseCase {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    public LoginMemberUseCaseImpl(AuthenticationManagerBuilder authenticationManagerBuilder,
        TokenProvider tokenProvider) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public LoginServiceResponse login(LoginServiceRequest dto) {
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        Authentication authentication;
        try {
            authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new LoginFailedException("비밀번호가 일치하지 않습니다.");
        } catch (InternalAuthenticationServiceException e) {
            throw new LoginFailedException("존재하지 않는 유저입니다.");
        }
        CustomDetails customDetails = (CustomDetails) authentication.getPrincipal();
        String email = customDetails.getUsername();
        String nickname = customDetails.getNickname();
        boolean isBlocked = customDetails.isBlocked();
        if (isBlocked) {
            throw new BlockMemberException("차단된 사용자입니다. 관리자에게 문의해주세요.");
        }
        String accessToken = tokenProvider.createToken(String.valueOf(customDetails.getId()),
            false);
        String refreshToken = tokenProvider.createToken(String.valueOf(customDetails.getId()),
            true);
        return new LoginServiceResponse(email, nickname, accessToken, refreshToken,
            isBlocked, customDetails.isAdmin());
    }
}
