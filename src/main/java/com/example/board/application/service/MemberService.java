package com.example.board.application.service;

import com.example.board.adapter.out.persistence.member.MemberRepositoryAdapter;
import com.example.board.adapter.ports.in.dto.response.member.MemberInfoResponse;
import com.example.board.application.port.in.member.DeleteMemberUseCase;
import com.example.board.application.port.in.member.GetMemberInfoUseCase;
import com.example.board.application.port.in.member.SearchMemberUseCase;
import com.example.board.application.port.in.member.SignupMemberUseCase;
import com.example.board.application.port.in.member.UpdateBlockStatusUseCase;
import com.example.board.application.port.in.member.UpdateNicknameUseCase;
import com.example.board.application.port.in.member.UpdatePasswordUseCase;
import com.example.board.application.service.dto.NicknameUpdateServiceDto;
import com.example.board.application.service.dto.PasswordUpdateServiceDto;
import com.example.board.application.service.dto.SignupServiceDto;
import com.example.board.domain.entity.Member;
import com.example.board.domain.entity.MemberRoleEnum;
import com.example.board.infrastructure.exception.DuplicateMemberException;
import com.example.board.infrastructure.exception.InconsistentNewPasswordException;
import com.example.board.infrastructure.exception.InconsistentOriginPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService implements SignupMemberUseCase, GetMemberInfoUseCase,
    UpdateNicknameUseCase, UpdatePasswordUseCase, DeleteMemberUseCase, SearchMemberUseCase,
    UpdateBlockStatusUseCase {

    private final MemberRepositoryAdapter memberRepositoryAdapter;
    private final PasswordEncoder encoder;

    @Autowired
    public MemberService(MemberRepositoryAdapter memberRepositoryAdapter, PasswordEncoder encoder) {
        this.memberRepositoryAdapter = memberRepositoryAdapter;
        this.encoder = encoder;
    }

    @Transactional
    @Override
    public void signup(SignupServiceDto dto, MemberRoleEnum role) {
        if (memberRepositoryAdapter.findByEmailAndIsDeletedFalse(dto.getEmail()) != null) {
            throw new DuplicateMemberException("\"이미 존재하는 회원입니다.\"");
        }
        Member member = Member.create(dto.getNickname(), dto.getEmail(),
            encoder.encode(dto.getPassword()),
            role == null ? MemberRoleEnum.USER : role);

        memberRepositoryAdapter.save(member);
    }

    @Transactional
    @Override
    public MemberInfoResponse getMemberInfo(String email) {
        Member member = memberRepositoryAdapter.findByEmailAndIsDeletedFalse(email);
        return member.toMemberInfoResponse();
    }

    @Transactional
    @Override
    public void updatePassword(PasswordUpdateServiceDto dto, String email) {
        Member member = memberRepositoryAdapter.findByEmailAndIsDeletedFalse(email);
        if (!encoder.matches(dto.getOriginPassword(), member.getPassword())) {
            throw new InconsistentOriginPasswordException("기존 비밀번호가 일치하지 않습니다.");
        }
        if (!dto.getNewPassword().equals(dto.getNewPasswordConfirm())) {
            throw new InconsistentNewPasswordException("변경할 비밀번호가 일치하지 않습니다.");
        }
        member.updatePassword(encoder.encode(dto.getNewPassword()));
    }

    @Transactional
    @Override
    public void updateNickname(NicknameUpdateServiceDto dto, String email) {
        Member member = memberRepositoryAdapter.findByEmailAndIsDeletedFalse(email);
        member.updateNickname(dto.getNewNickname());
    }

    @Transactional
    @Override
    public void delete(String email) {
        Member member = memberRepositoryAdapter.findByEmailAndIsDeletedFalse(email);
        member.delete();  // Soft Delete 방법 적용
    }

    @Transactional
    @Override
    public Page<MemberInfoResponse> search(String keyword, Pageable pageable) {
        return memberRepositoryAdapter.search(keyword, pageable);
    }

    @Transactional
    @Override
    public void updateBlockStatus(Long memberId, boolean isBlock) {
        Member member = memberRepositoryAdapter.findById(memberId);
        member.updateBlockStatus(isBlock);
    }
}
