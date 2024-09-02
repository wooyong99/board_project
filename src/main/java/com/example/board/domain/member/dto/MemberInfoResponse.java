package com.example.board.domain.member.dto;

import com.example.board.domain.member.entity.MemberRoleEnum;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class MemberInfoResponse {

    private Long memberId;
    private String nickname;
    private String email;
    private LocalDateTime createdAt;
    private MemberRoleEnum role;
    private boolean isBlock;

    @QueryProjection
    public MemberInfoResponse(Long memberId, String nickname, String email, LocalDateTime createdAt,
        MemberRoleEnum role, boolean isBlock) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.email = email;
        this.createdAt = createdAt;
        this.role = role;
        this.isBlock = isBlock;
    }

}
