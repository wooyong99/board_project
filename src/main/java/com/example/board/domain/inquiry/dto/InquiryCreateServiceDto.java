package com.example.board.domain.inquiry.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class InquiryCreateServiceDto {

    private String email;
    private String password;
    private String content;

    @Builder
    public InquiryCreateServiceDto(String email, String password, String content) {
        this.email = email;
        this.password = password;
        this.content = content;
    }

}
