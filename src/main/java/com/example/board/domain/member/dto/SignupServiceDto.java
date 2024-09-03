package com.example.board.domain.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SignupServiceDto {

    private String nickname;
    private String email;
    private String password;

    @Builder
    public SignupServiceDto(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

}
