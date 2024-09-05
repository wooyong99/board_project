package com.example.board.application.service.dto;

import com.example.board.adapter.ports.in.dto.request.member.NicknameUpdateRequest;
import lombok.Data;

@Data
public class NicknameUpdateServiceDto {

    private String newNickname;

    public NicknameUpdateServiceDto(NicknameUpdateRequest request) {
        this.newNickname = request.getNewNickname();
    }

}
