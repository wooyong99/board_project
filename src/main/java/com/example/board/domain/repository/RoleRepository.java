package com.example.board.domain.repository;

import com.example.board.domain.entity.Role;
import com.example.board.domain.entity.RoleEnum;
import java.util.Optional;

public interface RoleRepository {

    Optional<Role> findByName(RoleEnum name);

}
