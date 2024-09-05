package com.example.board.application.port.in.member;

import com.example.board.application.service.dto.SignupServiceDto;
import com.example.board.domain.entity.MemberRoleEnum;

public interface SignupMemberUseCase {

    void signup(SignupServiceDto dto, MemberRoleEnum role);
}
