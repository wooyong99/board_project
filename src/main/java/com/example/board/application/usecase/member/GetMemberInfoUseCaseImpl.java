package com.example.board.application.usecase.member;

import com.example.board.api.controller.member.response.MemberInfoResponse;
import com.example.board.application.usecase.member.dto.GetMemberInfoServiceDto;
import com.example.board.domain.entity.Member;
import com.example.board.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class GetMemberInfoUseCaseImpl implements GetMemberInfoUseCase {

    private final MemberRepository memberRepository;

    public GetMemberInfoUseCaseImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberInfoResponse getMemberInfo(GetMemberInfoServiceDto dto) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(dto.getEmail());
        return member.toMemberInfoResponse();
    }
}
