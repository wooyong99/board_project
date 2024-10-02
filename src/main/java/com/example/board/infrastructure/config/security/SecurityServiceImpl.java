package com.example.board.infrastructure.config.security;

import com.example.board.domain.entity.Member;
import com.example.board.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final MemberRepository memberRepository;

    @Override
    public Authentication getAuthentication(String memberId) {
        Member member = getMember(memberId);

        return createAuthentication(member);
    }

    private UsernamePasswordAuthenticationToken createAuthentication(Member user) {
        return new AuthenticationToken(user);
    }

    private Member getMember(String memberId) {
        long id = Long.parseLong(memberId);

        return memberRepository.findWithRoleByIdAndIsDeletedFalse(id);
    }

//    private GrantedAuthority userRoleToAuthorities(UserRole userRole) {
//        return new SimpleGrantedAuthority(userRole.name());
//    }
}
