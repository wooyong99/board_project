package com.example.board.application.usecase.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateNicknameServiceDto {

    private String newNickname;

    private String email;
}
