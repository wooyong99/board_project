package com.example.board.api.controller.member.response;

import com.example.board.domain.entity.MemberRole;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdAt;
    private MemberRole role;
    private Boolean isBlock;

    public MemberInfoResponse() {
    }

    @QueryProjection
    public MemberInfoResponse(Long memberId, String nickname, String email, LocalDateTime createdAt,
        MemberRole role, boolean isBlock) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.email = email;
        this.createdAt = createdAt;
        this.role = role;
        this.isBlock = isBlock;
    }

}
