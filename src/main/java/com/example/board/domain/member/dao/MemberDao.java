package com.example.board.domain.member.dao;

import com.example.board.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDao extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}
