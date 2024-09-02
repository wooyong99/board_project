package com.example.board.domain.inquiry.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InquiryDetailResponse {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private String author;

}
