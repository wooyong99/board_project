package com.example.board.application.usecase.post;

import com.example.board.api.controller.post.response.PostDetailResponse;
import com.example.board.application.usecase.post.dto.GetPostDetailServiceDto;

public interface GetPostDetailUseCase {

    PostDetailResponse getPost(GetPostDetailServiceDto dto);
}
