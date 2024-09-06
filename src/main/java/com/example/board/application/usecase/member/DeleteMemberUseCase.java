package com.example.board.application.usecase.member;

import com.example.board.application.usecase.member.dto.DeleteMemberServiceDto;

public interface DeleteMemberUseCase {

    void delete(DeleteMemberServiceDto dto);
}
