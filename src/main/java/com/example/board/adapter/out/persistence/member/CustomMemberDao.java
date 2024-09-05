package com.example.board.adapter.ports.out.persistence.member;

import com.example.board.adapter.ports.in.dto.response.member.MemberInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CustomMemberDao {

    Page<MemberInfoResponse> search(String keyword, Pageable pageable);

}
