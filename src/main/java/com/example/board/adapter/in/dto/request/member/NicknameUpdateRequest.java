package com.example.board.adapter.ports.in.dto.request.member;

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

}
