package com.example.board.application.usecase.member;

import com.example.board.application.usecase.member.dto.LogoutMemberServiceRequest;

public interface LogoutMemberUseCase {

    void logout(LogoutMemberServiceRequest dto);
}
