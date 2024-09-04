package com.example.board.persistence.member.dao;

import com.example.board.persistence.member.dto.MemberInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomMemberDao {

    Page<MemberInfoResponse> search(String keyword, Pageable pageable);

}
