package com.example.board.domain.entity;

import com.example.board.api.controller.member.response.MemberInfoResponse;
import com.example.board.domain.model.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    private LocalDateTime blockedAt;

    @ColumnDefault("false")
    private boolean isDeleted;

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST,
        CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MemberRole> roles = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = {CascadeType.PERSIST,
        CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    private Inquiry inquiry;

    protected Member() {
    }

    public Member(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public static Member create(String nickname, String email, String password,
        RoleEnum role) {
        return new Member(nickname, email, password);
    }

    public MemberInfoResponse toMemberInfoResponse() {
        return MemberInfoResponse.builder()
            .memberId(this.getId())
            .nickname(this.nickname)
            .email(this.email)
            .createdAt(this.getCreatedAt())
            .role(this.roles.get(0))
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

    public void updateBlockedAt() {
        this.blockedAt = LocalDateTime.now();
    }
}
