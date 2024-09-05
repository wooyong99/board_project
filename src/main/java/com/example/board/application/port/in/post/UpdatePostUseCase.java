package com.example.board.application.port.in.post;

import com.example.board.application.service.dto.PostUpdateServiceDto;

public interface UpdatePostUseCase {

    void update(Long postId, PostUpdateServiceDto dto, String email);
}
