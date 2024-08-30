package com.example.board.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordUpdateRequest {

    @NotBlank
    private String originPassword;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String newPasswordConfirm;

}
