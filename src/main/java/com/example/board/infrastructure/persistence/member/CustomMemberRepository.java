package com.example.board.infrastructure.persistence.member;

import com.example.board.api.controller.member.response.MemberInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CustomMemberRepository {

    Page<MemberInfoResponse> search(String keyword, Pageable pageable);

}
