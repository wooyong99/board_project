package com.example.board.application.usecase.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateNicknameServiceRequest {

    private String newNickname;

    private Long memberId;
}
