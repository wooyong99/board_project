package com.example.board.persistence.member.dto;

import lombok.Data;

@Data
public class NicknameUpdateServiceDto {

    private String newNickname;

    public NicknameUpdateServiceDto(NicknameUpdateRequest request) {
        this.newNickname = request.getNewNickname();
    }

}
