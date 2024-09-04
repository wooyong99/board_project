package com.example.board.domain.inquiry.dto;

import lombok.Data;

@Data
public class InquiryCreateServiceDto {

    private String email;
    private String password;
    private String content;

    public InquiryCreateServiceDto(InquiryCreateRequest request) {
        this.email = request.getEmail();
        this.password = request.getPassword();
        this.content = request.getContent();
    }
}
