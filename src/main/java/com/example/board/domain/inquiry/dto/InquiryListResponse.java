package com.example.board.domain.inquiry.dto;


import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InquiryListResponse {

    private Long id;
    private String nickname;
    private String email;
    private LocalDateTime createdAt;
    private boolean isAuthorBlock;

    @QueryProjection
    public InquiryListResponse(Long id, String nickname, String email, LocalDateTime createdAt,
        boolean isAuthorBlock) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.createdAt = createdAt;
        this.isAuthorBlock = isAuthorBlock;
    }


}
