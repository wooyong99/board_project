package com.example.board.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NicknameUpdateRequest {

    @NotBlank
    private String newNickname;

    public NicknameUpdateRequest() {
    }

    public NicknameUpdateRequest(String newNickname) {
        this.newNickname = newNickname;
    }

    public NicknameUpdateServiceDto toServiceDto() {
        return NicknameUpdateServiceDto.builder()
            .newNickname(this.newNickname)
            .build();
    }
}
