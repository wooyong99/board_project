package com.example.board.domain.entity;

import com.example.board.adapter.ports.in.dto.response.member.MemberInfoResponse;
import com.example.board.domain.model.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;


@Entity
@Getter
@Table(name = "members")
public class Member extends BaseEntity {

    private String nickname;

    @Column(nullable = false)
    private String email;

    private String password;

    private boolean isBlock;

    @Column
    @Enumerated(EnumType.STRING)
    private MemberRoleEnum role;

    @ColumnDefault("false")
    private boolean isDeleted;

    @OneToOne(mappedBy = "member", cascade = {CascadeType.PERSIST,
        CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    private Inquiry inquiry;

    protected Member() {
    }

    public Member(String nickname, String email, String password, MemberRoleEnum role) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static Member create(String nickname, String email, String password,
        MemberRoleEnum role) {
        return new Member(nickname, email, password, role);
    }

    public MemberInfoResponse toMemberInfoResponse() {
        return MemberInfoResponse.builder()
            .memberId(this.getId())
            .nickname(this.nickname)
            .email(this.email)
            .createdAt(this.getCreatedAt())
            .role(this.role)
            .isBlock(this.isBlock)
            .build();
    }

    public void updateBlockStatus(boolean isBlock) {
        this.isBlock = isBlock;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
