package com.example.board.adapter.ports.in.dto.request.member;

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
}
