package com.example.board.application.port.in.comment;

import com.example.board.application.service.dto.CommentUpdateServiceDto;

public interface UpdateCommentUseCase {

    void update(Long postId, Long commentId, CommentUpdateServiceDto dto,
        String email);
}
