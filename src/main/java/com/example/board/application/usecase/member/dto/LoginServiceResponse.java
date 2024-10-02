package com.example.board.application.usecase.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginServiceResponse {

    private String email;
    private String nickname;
    private String accessToken;
    private String refreshToken;
    private boolean isBlocked;
    private boolean isAdmin;

}
