package com.example.board.domain.member.dto;

import lombok.Data;

@Data
public class SignupServiceDto {

    private String nickname;
    private String email;
    private String password;

    public SignupServiceDto(SignupRequest request) {
        this.nickname = request.getNickname();
        this.email = request.getEmail();
        this.password = request.getPassword();
    }
}
