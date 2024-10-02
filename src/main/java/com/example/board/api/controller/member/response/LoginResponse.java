package com.example.board.api.controller.member.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String email;

    private String nickname;

    private String accessToken;

    private Boolean isBlocked;

    private Boolean isAdmin;

}
