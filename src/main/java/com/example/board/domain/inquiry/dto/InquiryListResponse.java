package com.example.board.domain.inquiry.dto;


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
    private boolean isAuthorBlock;

    public InquiryListResponse() {
    }

    @QueryProjection
    @Builder
    public InquiryListResponse(Long id, String nickname, String email, LocalDateTime createdAt,
        boolean isAuthorBlock) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.createdAt = createdAt;
        this.isAuthorBlock = isAuthorBlock;
    }
}
