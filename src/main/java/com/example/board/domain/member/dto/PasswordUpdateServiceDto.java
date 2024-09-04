package com.example.board.domain.member.dto;

import lombok.Data;

@Data
public class PasswordUpdateServiceDto {

    private String originPassword;
    private String newPassword;
    private String newPasswordConfirm;

    public PasswordUpdateServiceDto(PasswordUpdateRequest request) {
        this.originPassword = request.getOriginPassword();
        this.newPassword = request.getNewPassword();
        this.newPasswordConfirm = request.getNewPasswordConfirm();
    }
}
