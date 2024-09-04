package com.example.board.persistence.inquiry.dto;


import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
public class InquiryListResponse {

    private Long id;
    private String nickname;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime memberUpdatedAt;
    private boolean isAuthorBlock;
    private Long memberId;

    public InquiryListResponse() {
    }

    @QueryProjection
    @Builder
    public InquiryListResponse(Long id, String nickname, String email, LocalDateTime createdAt,
        LocalDateTime memberUpdatedAt,
        boolean isAuthorBlock, Long memberId) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.createdAt = createdAt;
        this.memberUpdatedAt = memberUpdatedAt;
        this.isAuthorBlock = isAuthorBlock;
        this.memberId = memberId;
    }
}
