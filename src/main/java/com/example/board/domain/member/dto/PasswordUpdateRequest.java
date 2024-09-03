package com.example.board.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordUpdateRequest {

    @NotBlank
    private String originPassword;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String newPasswordConfirm;

    public PasswordUpdateRequest() {
    }

    public PasswordUpdateRequest(String originPassword, String newPassword,
        String newPasswordConfirm) {
        this.originPassword = originPassword;
        this.newPassword = newPassword;
        this.newPasswordConfirm = newPasswordConfirm;
    }

    public PasswordUpdateServiceDto toServiceDto() {
        return PasswordUpdateServiceDto.builder()
            .originPassword(this.originPassword)
            .newPassword(this.newPassword)
            .newPasswordConfirm(this.newPasswordConfirm)
            .build();
    }
}
