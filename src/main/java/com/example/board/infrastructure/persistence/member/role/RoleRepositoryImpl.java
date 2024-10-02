package com.example.board.infrastructure.persistence.member.role;

import com.example.board.domain.entity.Role;
import com.example.board.domain.entity.RoleEnum;
import com.example.board.domain.repository.RoleRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleJpaRepository roleJpaRepository;

    public RoleRepositoryImpl(RoleJpaRepository roleJpaRepository) {
        this.roleJpaRepository = roleJpaRepository;
    }

    @Override
    public Optional<Role> findByName(RoleEnum name) {
        return roleJpaRepository.findByName(name);
    }
}
