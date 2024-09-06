package com.example.board.application.usecase.post;

import com.example.board.application.usecase.post.dto.SavePostServiceDto;

public interface SavePostUseCase {

    void save(SavePostServiceDto dto);
}
