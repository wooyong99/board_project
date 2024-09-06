package com.example.board.api.controller.inquiry.request;

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
