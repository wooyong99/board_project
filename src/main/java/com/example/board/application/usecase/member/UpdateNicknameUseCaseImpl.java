package com.example.board.application.usecase.member;

import com.example.board.application.usecase.member.dto.UpdateNicknameServiceRequest;
import com.example.board.domain.entity.Member;
import com.example.board.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateNicknameUseCaseImpl implements UpdateNicknameUseCase {

    private final MemberRepository memberRepository;

    public UpdateNicknameUseCaseImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public void updateNickname(UpdateNicknameServiceRequest dto) {
        Member member = memberRepository.findByIdAndIsDeletedFalse(dto.getMemberId());
        member.updateNickname(dto.getNewNickname());
    }
}
