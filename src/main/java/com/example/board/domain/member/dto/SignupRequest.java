package com.example.board.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotBlank
    private String nickname;

    @NotBlank
    private String email;

    @NotBlank(message = "비밀번호는 공백, 빈칸, null값을 허용하지 않습니다.")
    private String password;

}
