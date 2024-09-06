package com.example.board.api.controller.member.request;

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
