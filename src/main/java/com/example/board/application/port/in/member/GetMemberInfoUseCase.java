package com.example.board.application.port.in.member;

import com.example.board.adapter.ports.in.dto.response.member.MemberInfoResponse;

public interface GetMemberInfoUseCase {

    MemberInfoResponse getMemberInfo(String email);
}
