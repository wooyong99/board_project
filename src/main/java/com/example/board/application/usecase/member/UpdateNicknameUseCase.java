package com.example.board.application.usecase.member;

import com.example.board.application.usecase.member.dto.UpdateNicknameServiceRequest;

public interface UpdateNicknameUseCase {

    void updateNickname(UpdateNicknameServiceRequest dto);
}
