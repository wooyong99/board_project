package com.example.board.infrastructure.persistence.post;

import com.example.board.api.controller.post.response.PostDetailResponse;
import com.example.board.api.controller.post.response.PostListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomPostRepository {

    Page<PostListResponse> findPostList(Long categoryId, String keyword, Pageable pageable);

    PostDetailResponse findPost(Long postId);

}
