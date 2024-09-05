package com.example.board.infrastructure.persistence.post;

import com.example.board.adapter.ports.in.dto.response.post.PostDetailResponse;
import com.example.board.adapter.ports.in.dto.response.post.PostListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomPostRepository {

    Page<PostListResponse> findPostList(Long categoryId, String keyword, Pageable pageable);

    PostDetailResponse findPost(Long postId);

}
