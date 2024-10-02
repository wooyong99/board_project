package com.example.board.application.usecase.member;

import com.example.board.application.usecase.member.dto.SignupMemberServiceRequest;

public interface SignupMemberUseCase {

    void signup(SignupMemberServiceRequest dto);
}
