package com.example.board.api.controller.member.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;

    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
