package com.example.board.application.port.in.post;

public interface DeletePostUseCase {

    void delete(Long postId, String email);
}
