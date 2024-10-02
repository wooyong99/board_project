package com.example.board.application.usecase.member;

import com.example.board.application.usecase.member.dto.UpdatePasswordServiceRequest;
import com.example.board.domain.entity.Member;
import com.example.board.domain.repository.MemberRepository;
import com.example.board.exception.InconsistentNewPasswordException;
import com.example.board.exception.InconsistentOriginPasswordException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdatePasswordUseCaseImpl implements UpdatePasswordUseCase {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    public UpdatePasswordUseCaseImpl(MemberRepository memberRepository, PasswordEncoder encoder) {
        this.memberRepository = memberRepository;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public void updatePassword(UpdatePasswordServiceRequest dto) {
        Member member = memberRepository.findByIdAndIsDeletedFalse(dto.getMemberId());
        if (!encoder.matches(dto.getOriginPassword(), member.getPassword())) {
            throw new InconsistentOriginPasswordException("기존 비밀번호가 일치하지 않습니다.");
        }
        if (!dto.getNewPassword().equals(dto.getNewPasswordConfirm())) {
            throw new InconsistentNewPasswordException("변경할 비밀번호가 일치하지 않습니다.");
        }
        member.updatePassword(encoder.encode(dto.getNewPassword()));
    }
}
