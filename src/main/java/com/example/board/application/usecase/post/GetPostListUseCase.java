package com.example.board.application.usecase.post;

import com.example.board.api.controller.post.response.PostListResponse;
import com.example.board.application.usecase.post.dto.GetPostListServiceDto;
import org.springframework.data.domain.Page;

public interface GetPostListUseCase {

    Page<PostListResponse> getPostList(GetPostListServiceDto dto);
}
