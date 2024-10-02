package com.example.board.api.controller.inquiry.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
public class InquiryDetailResponse {

    private Long id;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
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
