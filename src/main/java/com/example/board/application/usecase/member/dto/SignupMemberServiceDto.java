package com.example.board.application.usecase.member.dto;

import com.example.board.domain.entity.MemberRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupMemberServiceDto {

    private String nickname;
    private String email;
    private String password;
    private MemberRoleEnum role;

}
