package com.example.board.domain.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class PasswordUpdateServiceDto {

    private String originPassword;
    private String newPassword;
    private String newPasswordConfirm;

    @Builder
    public PasswordUpdateServiceDto(String originPassword, String newPassword,
        String newPasswordConfirm) {
        this.originPassword = originPassword;
        this.newPassword = newPassword;
        this.newPasswordConfirm = newPasswordConfirm;
    }
}
