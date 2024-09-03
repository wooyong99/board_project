package com.example.board.domain.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class NicknameUpdateServiceDto {

    private String newNickname;

    @Builder
    public NicknameUpdateServiceDto(String newNickname) {
        this.newNickname = newNickname;
    }

}
