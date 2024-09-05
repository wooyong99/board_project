package com.example.board.application.port.in.post;

import com.example.board.application.service.dto.PostCreateServiceDto;

public interface SavePostUseCase {

    void save(PostCreateServiceDto dto, String email);
}
