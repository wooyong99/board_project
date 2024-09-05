package com.example.board.application.service.dto;

import com.example.board.adapter.ports.in.dto.request.inquiry.InquiryCreateRequest;
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
