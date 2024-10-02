package com.example.board.application.usecase.member;

import com.example.board.api.controller.member.response.MemberInfoResponse;
import com.example.board.application.usecase.member.dto.GetMemberInfoServiceRequest;

public interface GetMemberInfoUseCase {

    MemberInfoResponse getMemberInfo(GetMemberInfoServiceRequest dto);
}
