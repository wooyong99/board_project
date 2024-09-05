package com.example.board.application.port.in.post;

import com.example.board.adapter.ports.in.dto.response.post.PostListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetPostListUseCase {

    Page<PostListResponse> getPostList(Long categoryId, Pageable pageable);
}
