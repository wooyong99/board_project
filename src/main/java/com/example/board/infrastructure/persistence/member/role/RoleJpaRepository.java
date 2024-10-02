package com.example.board.infrastructure.persistence.member.role;

import com.example.board.domain.entity.Role;
import com.example.board.domain.entity.RoleEnum;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJpaRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleEnum name);

}
