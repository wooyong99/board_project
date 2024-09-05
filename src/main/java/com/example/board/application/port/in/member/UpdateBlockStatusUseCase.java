package com.example.board.application.port.in.member;

public interface UpdateBlockStatusUseCase {

    void updateBlockStatus(Long memberId, boolean isBlock);
}
