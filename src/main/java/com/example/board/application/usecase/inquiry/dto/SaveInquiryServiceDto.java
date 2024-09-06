package com.example.board.application.usecase.inquiry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveInquiryServiceDto {

    private String email;
    private String password;
    private String content;

}
