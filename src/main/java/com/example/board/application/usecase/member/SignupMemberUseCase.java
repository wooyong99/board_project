package com.example.board.application.usecase.member;

import com.example.board.application.usecase.member.dto.SignupMemberServiceDto;

public interface SignupMemberUseCase {

    void signup(SignupMemberServiceDto dto);
}
