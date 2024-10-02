package com.example.board.infrastructure.config.security;

import com.example.board.domain.entity.Member;
import com.example.board.domain.entity.MemberRole;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public class AuthenticationToken extends UsernamePasswordAuthenticationToken {

    private Member member;

    public AuthenticationToken(Member member) {
        super(member, null, authorities(member.getRoles()));
        this.member = member;
    }

    private static Collection<? extends GrantedAuthority> authorities(List<MemberRole> roles) {
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority(
                role.getRole().getName().toString())) // 각 Role의 name을 기반으로 GrantedAuthority 생성
            .collect(Collectors.toSet()); // Set으로 변환하여 반환
    }

//    @Override
//    public Map<String, Object> getAttributes() {
//        return null;
//    }

    @Override
    public String getName() {
        return String.valueOf(member.getId());
    }
}
