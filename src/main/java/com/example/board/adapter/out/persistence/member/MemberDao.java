package com.example.board.adapter.ports.out.persistence.member;

import com.example.board.adapter.ports.in.dto.response.member.MemberInfoResponse;
import com.example.board.domain.entity.Member;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDao extends JpaRepository<Member, Long>, CustomMemberDao {

    Optional<Member> findByEmailAndIsDeletedFalse(String email);

    @Override
    Page<MemberInfoResponse> search(String keyword, Pageable pageable);
}
