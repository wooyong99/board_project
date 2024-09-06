package com.example.board.application.usecase.member;

import com.example.board.api.controller.member.response.MemberInfoResponse;
import com.example.board.application.usecase.member.dto.SearchMemberServiceDto;
import com.example.board.domain.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SearchMemberUseCaseImpl implements SearchMemberUseCase {

    private final MemberRepository memberRepository;

    public SearchMemberUseCaseImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Page<MemberInfoResponse> search(SearchMemberServiceDto dto) {
        return memberRepository.search(dto.getKeyword(), dto.getPageable());
    }
}
