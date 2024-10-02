package com.example.board.application.usecase.member;

import com.example.board.application.usecase.member.dto.UpdateBlockStatusServiceRequest;

public interface UpdateBlockStatusUseCase {

    void updateBlockStatus(UpdateBlockStatusServiceRequest dto);
}
