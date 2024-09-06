package com.example.board.application.usecase.member;

import com.example.board.application.usecase.member.dto.UpdateNicknameServiceDto;
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
    public void updateNickname(UpdateNicknameServiceDto dto) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(dto.getEmail());
        member.updateNickname(dto.getNewNickname());
    }
}
