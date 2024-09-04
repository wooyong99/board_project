package com.example.board.persistence.inquiry.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InquiryCreateRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String content;

    public InquiryCreateRequest() {
    }

    public InquiryCreateRequest(String email, String password, String content) {
        this.email = email;
        this.password = password;
        this.content = content;
    }
}
