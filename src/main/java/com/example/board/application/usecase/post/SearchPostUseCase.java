package com.example.board.application.usecase.post;

import com.example.board.api.controller.post.response.PostListResponse;
import com.example.board.application.usecase.post.dto.SearchPostServiceDto;
import org.springframework.data.domain.Page;

public interface SearchPostUseCase {

    Page<PostListResponse> search(SearchPostServiceDto dto);
}
