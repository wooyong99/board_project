package com.example.board.application.port.in.comment;

public interface DeleteCommentUseCase {

    void delete(Long postId, Long commentId, String email);
}
