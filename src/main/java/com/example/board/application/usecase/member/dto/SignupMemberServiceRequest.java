package com.example.board.application.usecase.member.dto;

import com.example.board.domain.entity.RoleEnum;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupMemberServiceRequest {

    private String nickname;
    private String email;
    private String password;
    private List<RoleEnum> roles;

}
