package com.example.board.application.usecase.member;

import com.example.board.application.usecase.member.dto.ReissueMemberServiceRequest;
import com.example.board.application.usecase.member.dto.ReissueMemberServiceResponse;

public interface ReissueMemberUseCase {

    ReissueMemberServiceResponse reissue(ReissueMemberServiceRequest dto);
}
