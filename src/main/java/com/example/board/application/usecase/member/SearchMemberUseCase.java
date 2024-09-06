package com.example.board.application.usecase.member;

import com.example.board.api.controller.member.response.MemberInfoResponse;
import com.example.board.application.usecase.member.dto.SearchMemberServiceDto;
import org.springframework.data.domain.Page;

public interface SearchMemberUseCase {

    Page<MemberInfoResponse> search(SearchMemberServiceDto dto);
}
