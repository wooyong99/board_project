package com.example.board.domain.member.dao;

import com.example.board.domain.member.dto.MemberInfoResponse;
import com.example.board.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDao extends JpaRepository<Member, Long>, CustomMemberDao {

    Optional<Member> findByEmailAndIsDeletedFalse(String email);

    @Override
    Page<MemberInfoResponse> search(String keyword, Pageable pageable);
}
