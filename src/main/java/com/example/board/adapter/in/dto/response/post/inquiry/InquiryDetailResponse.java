package com.example.board.adapter.ports.in.dto.response.post.inquiry;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
public class InquiryDetailResponse {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private String author;

    public InquiryDetailResponse() {
    }

    @Builder
    public InquiryDetailResponse(Long id, String content, LocalDateTime createdAt, String author) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.author = author;
    }

}
