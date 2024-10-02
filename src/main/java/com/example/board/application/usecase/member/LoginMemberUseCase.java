package com.example.board.application.usecase.member;

import com.example.board.application.usecase.member.dto.LoginServiceRequest;
import com.example.board.application.usecase.member.dto.LoginServiceResponse;

public interface LoginMemberUseCase {

    LoginServiceResponse login(LoginServiceRequest dto);
}
