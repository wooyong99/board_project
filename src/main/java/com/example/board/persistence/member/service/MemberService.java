package com.example.board.persistence.member.service;

import com.example.board.global.exception.DuplicateMemberException;
import com.example.board.global.exception.InconsistentNewPasswordException;
import com.example.board.global.exception.InconsistentOriginPasswordException;
import com.example.board.global.exception.NotFoundMemberException;
import com.example.board.persistence.entity.Member;
import com.example.board.persistence.member.dao.MemberDao;
import com.example.board.persistence.member.dto.MemberInfoResponse;
import com.example.board.persistence.member.dto.NicknameUpdateServiceDto;
import com.example.board.persistence.member.dto.PasswordUpdateServiceDto;
import com.example.board.persistence.member.dto.SignupServiceDto;
import com.example.board.persistence.member.entity.MemberRoleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberDao memberDao;
    private final PasswordEncoder encoder;

    public MemberService(MemberDao memberDao, PasswordEncoder encoder) {
        this.memberDao = memberDao;
        this.encoder = encoder;
    }

    @Transactional
    public void signup(SignupServiceDto dto, MemberRoleEnum role) {
        if (memberDao.findByEmailAndIsDeletedFalse(dto.getEmail()).isPresent()) {
            throw new DuplicateMemberException("\"이미 존재하는 회원입니다.\"");
        }
        Member member = Member.create(dto.getNickname(), dto.getPassword(), dto.getPassword(),
            role == null ? MemberRoleEnum.USER : role);

        memberDao.save(member);
    }

    @Transactional
    public MemberInfoResponse getMemberInfo(String email) {
        Member member = findMemberByEmail(email);
        return member.toMemberInfoResponse();
    }

    @Transactional
    public void updatePassword(PasswordUpdateServiceDto dto, String email) {
        Member member = findMemberByEmail(email);
        if (!encoder.matches(dto.getOriginPassword(), member.getPassword())) {
            throw new InconsistentOriginPasswordException("기존 비밀번호가 일치하지 않습니다.");
        }
        if (!dto.getNewPassword().equals(dto.getNewPasswordConfirm())) {
            throw new InconsistentNewPasswordException("변경할 비밀번호가 일치하지 않습니다.");
        }
        member.updatePassword(encoder.encode(dto.getNewPassword()));
    }

    @Transactional
    public void updateNickname(NicknameUpdateServiceDto dto, String email) {
        Member member = findMemberByEmail(email);
        member.updateNickname(dto.getNewNickname());
    }

    private Member findMemberByEmail(String email) {
        return memberDao.findByEmailAndIsDeletedFalse(email).orElseThrow(
            () -> new NotFoundMemberException("존재하지 않는 사용자입니다.")
        );
    }

    @Transactional
    public void delete(String email) {
        Member member = findMemberByEmail(email);
        member.delete();  // Soft Delete 방법 적용
    }

    public Page<MemberInfoResponse> search(String keyword, Pageable pageable) {
        return memberDao.search(keyword, pageable);
    }


    @Transactional
    public void block(Long memberId, boolean isBlock) {
        Member member = findMemberById(memberId);
        member.updateBlockStatus(isBlock);
    }

    private Member findMemberById(Long memberId) {
        return memberDao.findById(memberId).orElseThrow(
            () -> new NotFoundMemberException("존재하지 않는 사용자입니다.")
        );
    }
}
