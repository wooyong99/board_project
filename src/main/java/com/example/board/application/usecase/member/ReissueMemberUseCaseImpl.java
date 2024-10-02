package com.example.board.application.usecase.member;

import com.example.board.application.usecase.member.dto.ReissueMemberServiceRequest;
import com.example.board.application.usecase.member.dto.ReissueMemberServiceResponse;
import com.example.board.domain.entity.Member;
import com.example.board.domain.repository.MemberRepository;
import com.example.board.exception.NotFoundMemberException;
import com.example.board.infrastructure.config.security.TokenProvider;
import com.example.board.infrastructure.utils.HttpServletUtils;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;

@Service
public class ReissueMemberUseCaseImpl implements ReissueMemberUseCase {

    private final HttpServletUtils servletUtils;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    public ReissueMemberUseCaseImpl(HttpServletUtils servletUtils, TokenProvider tokenProvider,
        MemberRepository memberRepository) {
        this.servletUtils = servletUtils;
        this.tokenProvider = tokenProvider;
        this.memberRepository = memberRepository;
    }

    @Override
    public ReissueMemberServiceResponse reissue(ReissueMemberServiceRequest dto) {
        String refreshToken = servletUtils.getCookie(dto.getRequest(), "RefreshToken")
            .map(Cookie::getValue)
            .orElse(null);
        if (tokenProvider.validateToken(refreshToken)) {
            String subject = tokenProvider.getSubject(refreshToken);
            Member member = memberRepository.findById(Long.parseLong(subject));
            String token = tokenProvider.createToken(tokenProvider.getSubject(refreshToken), false);
            return new ReissueMemberServiceResponse(member.getEmail(), member.getNickname(), token,
                member.isBlock(), member.getRoles().toString().equals("ADMIN") ? true : false);
        }
        throw new NotFoundMemberException("존재하지 않는 회원입니다.");
    }
}


