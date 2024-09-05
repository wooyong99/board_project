package com.example.board.application.port.in.member;

import com.example.board.application.service.dto.PasswordUpdateServiceDto;

public interface UpdatePasswordUseCase {

    void updatePassword(PasswordUpdateServiceDto dto, String email);
}
