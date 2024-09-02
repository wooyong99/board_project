package com.example.board.domain.member.entity;

import com.example.board.domain.inquiry.entity.Inquiry;
import com.example.board.domain.member.dto.MemberInfoResponse;
import com.example.board.domain.model.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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


    @OneToOne(mappedBy = "member", cascade = {CascadeType.PERSIST,
        CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    private Inquiry inquiry;

    @Builder
    public Member(String nickname, String email, String password, MemberRoleEnum role) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
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
}
