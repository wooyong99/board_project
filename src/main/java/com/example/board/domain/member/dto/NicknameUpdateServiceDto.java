package com.example.board.domain.member.dto;

import lombok.Data;

@Data
public class NicknameUpdateServiceDto {

    private String newNickname;

    public NicknameUpdateServiceDto(NicknameUpdateRequest request) {
        this.newNickname = request.getNewNickname();
    }

}
