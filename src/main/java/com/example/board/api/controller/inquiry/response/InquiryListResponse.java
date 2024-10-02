package com.example.board.api.controller.inquiry.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
public class InquiryListResponse {

    private Long id;
    private String nickname;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime blockedAt;
    private Boolean isAuthorBlock;
    private Long memberId;

    public InquiryListResponse() {
    }

    @QueryProjection
    @Builder
    public InquiryListResponse(Long id, String nickname, String email, LocalDateTime createdAt,
        LocalDateTime blockedAt,
        boolean isAuthorBlock, Long memberId) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.createdAt = createdAt;
        this.blockedAt = blockedAt;
        this.isAuthorBlock = isAuthorBlock;
        this.memberId = memberId;
    }
}
