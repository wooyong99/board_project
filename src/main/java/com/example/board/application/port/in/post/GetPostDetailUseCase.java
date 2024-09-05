package com.example.board.application.port.in.post;

import com.example.board.adapter.ports.in.dto.response.post.PostDetailResponse;

public interface GetPostDetailUseCase {

    PostDetailResponse getPost(Long postId);
}
