package com.example.board.api.controller.member.request;

import com.example.board.domain.entity.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Data;

@Data
public class AdminSignupRequest {

    @NotBlank
    private String nickname;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private List<RoleEnum> roles;

    public AdminSignupRequest() {
    }

    public AdminSignupRequest(String nickname, String email, String password,
        List<RoleEnum> roles) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}
