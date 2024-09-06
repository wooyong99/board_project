package com.example.board.application.usecase.member;

import com.example.board.application.usecase.member.dto.DeleteMemberServiceDto;
import com.example.board.domain.entity.Member;
import com.example.board.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteMemberUseCaseImpl implements DeleteMemberUseCase {

    private final MemberRepository memberRepository;

    public DeleteMemberUseCaseImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public void delete(DeleteMemberServiceDto dto) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(dto.getEmail());
        member.delete();  // Soft Delete 방법 적용
    }

}
