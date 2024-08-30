package com.example.board.domain.member.service;

import com.example.board.domain.member.dao.MemberDao;
import com.example.board.domain.member.dto.MemberInfoResponse;
import com.example.board.domain.member.dto.NicknameUpdateRequest;
import com.example.board.domain.member.dto.PasswordUpdateRequest;
import com.example.board.domain.member.dto.SignupRequest;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.entity.MemberRoleEnum;
import com.example.board.global.exception.DuplicateMemberException;
import com.example.board.global.exception.InconsistentNewPasswordException;
import com.example.board.global.exception.InconsistentOriginPasswordException;
import com.example.board.global.exception.NotFoundMemberException;
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

    @Transactional
    public MemberInfoResponse getMemberInfo(String email) {
        Member member = findMemberByEmail(email);
        return member.toMemberInfoResponse();
    }

    @Transactional
    public void updatePassword(PasswordUpdateRequest request, String email) {
        Member member = findMemberByEmail(email);
        if (!encoder.matches(request.getOriginPassword(), member.getPassword())) {
            throw new InconsistentOriginPasswordException("기존 비밀번호가 일치하지 않습니다.");
        }
        if (!request.getNewPassword().equals(request.getNewPasswordConfirm())) {
            throw new InconsistentNewPasswordException("변경할 비밀번호가 일치하지 않습니다.");
        }
        member.updatePassword(encoder.encode(request.getNewPassword()));
    }

    @Transactional
    public void updateNickname(NicknameUpdateRequest request, String email) {
        Member member = findMemberByEmail(email);
        member.updateNickname(request.getNewNickname());
    }

    private Member findMemberByEmail(String email) {
        return memberDao.findByEmail(email).orElseThrow(
            () -> new NotFoundMemberException("존재하지 않는 사용자입니다.")
        );
    }
}
