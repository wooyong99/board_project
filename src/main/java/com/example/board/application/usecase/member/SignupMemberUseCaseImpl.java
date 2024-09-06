package com.example.board.application.usecase.member;

import com.example.board.application.usecase.member.dto.SignupMemberServiceDto;
import com.example.board.domain.entity.Member;
import com.example.board.domain.entity.MemberRoleEnum;
import com.example.board.domain.repository.MemberRepository;
import com.example.board.exception.DuplicateMemberException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignupMemberUseCaseImpl implements SignupMemberUseCase {

    private final PasswordEncoder encoder;
    private final MemberRepository memberRepository;

    public SignupMemberUseCaseImpl(PasswordEncoder encoder, MemberRepository memberRepository) {
        this.encoder = encoder;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public void signup(SignupMemberServiceDto dto) {
        if (memberRepository.existsByEmailAndIsDeletedFalse(dto.getEmail())) {
            throw new DuplicateMemberException("\"이미 존재하는 회원입니다.\"");
        }
        Member member = Member.create(dto.getNickname(), dto.getEmail(),
            encoder.encode(dto.getPassword()),
            dto.getRole() == null ? MemberRoleEnum.USER : dto.getRole());

        memberRepository.save(member);
    }
}
