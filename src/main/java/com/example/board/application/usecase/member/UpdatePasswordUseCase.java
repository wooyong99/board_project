package com.example.board.application.usecase.member;

import com.example.board.application.usecase.member.dto.UpdatePasswordServiceRequest;

public interface UpdatePasswordUseCase {

    void updatePassword(UpdatePasswordServiceRequest dto);
}
