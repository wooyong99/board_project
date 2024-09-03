package com.example.board.global.security;

import com.example.board.domain.member.dao.MemberDao;
import com.example.board.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberDao memberDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberDao.findByEmailAndIsDeletedFalse(username)
            .orElseThrow(() -> new BadCredentialsException("이메일, 비밀번호를 확인해주세요."));
        if (member.isBlock()) {
            throw new BadCredentialsException("차단된 사용자입니다. 관리자에게 문의해주세요.");
        }
        return new CustomDetails(member);
    }

}
