package com.example.board.domain.member.service;

import com.example.board.domain.member.dao.MemberDao;
import com.example.board.domain.member.dto.SignupRequest;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.entity.MemberRoleEnum;
import com.example.board.global.exception.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberDao memberDao;
    private final PasswordEncoder encoder;

    @Transactional
    public void signup(SignupRequest request, MemberRoleEnum role) {
        if (memberDao.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateMemberException("\"이미 존재하는 회원입니다.\"");
        }
        Member member = Member.builder()
            .nickname(request.getNickname())
            .email(request.getEmail())
            .password(encoder.encode(request.getPassword()))
            .role(role == null ? MemberRoleEnum.USER : role)
            .build();
        memberDao.save(member);
    }
}
