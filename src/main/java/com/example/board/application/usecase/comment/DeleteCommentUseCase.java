package com.example.board.application.usecase.comment;

import com.example.board.application.usecase.comment.dto.DeleteCommentServiceDto;

public interface DeleteCommentUseCase {

    void delete(DeleteCommentServiceDto dto);
}
