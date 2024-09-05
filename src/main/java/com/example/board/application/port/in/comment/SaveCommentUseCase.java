package com.example.board.application.port.in.comment;

import com.example.board.application.service.dto.CommentCreateServiceDto;

public interface SaveCommentUseCase {

    void save(String email, Long postId, CommentCreateServiceDto dto);
}
