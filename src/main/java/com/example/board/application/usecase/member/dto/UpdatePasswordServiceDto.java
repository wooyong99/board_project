package com.example.board.application.usecase.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatePasswordServiceDto {

    private String originPassword;
    private String newPassword;
    private String newPasswordConfirm;
    private String email;
}
