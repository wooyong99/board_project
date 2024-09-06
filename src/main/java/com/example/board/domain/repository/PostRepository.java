package com.example.board.domain.repository;

import com.example.board.api.controller.post.response.PostDetailResponse;
import com.example.board.api.controller.post.response.PostListResponse;
import com.example.board.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepository {

    Post findById(Long postId);

    Page<PostListResponse> findPostList(Long categoryId, String keyword, Pageable pageable);

    PostDetailResponse findPost(Long postId);

    void save(Post post);
}
