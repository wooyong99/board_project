package com.example.board.domain.repository;

import com.example.board.api.controller.member.response.MemberInfoResponse;
import com.example.board.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepository {

    Member findByEmailAndIsDeletedFalse(String email);

    boolean existsByEmailAndIsDeletedFalse(String email);

    void save(Member member);

    Page<MemberInfoResponse> search(String keyword, Pageable pageable);

    Member findById(Long memberId);

    Member findByIdAndIsDeletedFalse(long id);

    Member findWithRoleByIdAndIsDeletedFalse(long id);
}
