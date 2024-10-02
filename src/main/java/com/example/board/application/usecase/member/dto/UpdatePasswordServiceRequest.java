package com.example.board.application.usecase.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatePasswordServiceRequest {

    private String originPassword;
    private String newPassword;
    private String newPasswordConfirm;
    private Long memberId;
}
