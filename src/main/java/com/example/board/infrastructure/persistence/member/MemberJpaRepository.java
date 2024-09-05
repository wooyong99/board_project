package com.example.board.infrastructure.persistence.member;

import com.example.board.domain.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmailAndIsDeletedFalse(String email);
}
