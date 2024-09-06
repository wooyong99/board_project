package com.example.board.application.usecase.member;

import com.example.board.application.usecase.member.dto.UpdateBlockStatusServiceDto;
import com.example.board.domain.entity.Member;
import com.example.board.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateBlockStatusUseCaseImpl implements UpdateBlockStatusUseCase {

    private final MemberRepository memberRepository;

    public UpdateBlockStatusUseCaseImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public void updateBlockStatus(UpdateBlockStatusServiceDto dto) {
        Member member = memberRepository.findById(dto.getMemberId());
        if (dto.isBlock()) {
            member.updateBlockedAt();
        }
        member.updateBlockStatus(dto.isBlock());
    }
}
