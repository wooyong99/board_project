package com.example.board.application.usecase.comment;

import com.example.board.application.usecase.comment.dto.SaveCommentServiceDto;

public interface SaveCommentUseCase {

    void save(SaveCommentServiceDto dto);
}
