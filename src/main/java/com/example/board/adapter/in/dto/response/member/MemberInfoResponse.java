package com.example.board.adapter.ports.in.dto.response.member;

import com.example.board.domain.entity.MemberRoleEnum;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberInfoResponse {

    private Long memberId;
    private String nickname;
    private String email;
    private LocalDateTime createdAt;
    private MemberRoleEnum role;
    private boolean isBlock;

    public MemberInfoResponse() {
    }

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
