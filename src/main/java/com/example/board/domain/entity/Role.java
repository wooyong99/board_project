package com.example.board.domain.entity;

import com.example.board.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    protected Role() {
    }

    public Role(RoleEnum name) {
        this.name = name;
    }

    public static Role create(RoleEnum name) {
        return new Role(name);
    }
}
