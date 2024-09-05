package com.example.board.application.port.in.member;

import com.example.board.adapter.ports.in.dto.response.member.MemberInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchMemberUseCase {

    Page<MemberInfoResponse> search(String keyword, Pageable pageable);
}
