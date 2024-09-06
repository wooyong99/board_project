package com.example.board.application.usecase.post;

import com.example.board.application.usecase.post.dto.LikePostServiceDto;

public interface LikePostUseCase {

    void like(LikePostServiceDto dto);
}
