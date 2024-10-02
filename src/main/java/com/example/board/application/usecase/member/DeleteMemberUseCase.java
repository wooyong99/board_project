package com.example.board.application.usecase.member;

import com.example.board.application.usecase.member.dto.DeleteMemberServiceRequest;

public interface DeleteMemberUseCase {

    void delete(DeleteMemberServiceRequest dto);
}
