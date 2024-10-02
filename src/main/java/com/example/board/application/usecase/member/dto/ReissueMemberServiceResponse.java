package com.example.board.application.usecase.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReissueMemberServiceResponse {

    private String email;
    private String nickname;
    private String accessToken;
    private Boolean isBlocked;
    private Boolean isAdmin;

}
