package com.example.board.application.port.in.member;

import com.example.board.application.service.dto.NicknameUpdateServiceDto;

public interface UpdateNicknameUseCase {

    void updateNickname(NicknameUpdateServiceDto dto, String email);
}
