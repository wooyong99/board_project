package com.example.board.domain.post.dao;

import com.example.board.domain.post.dto.PostDetailResponse;
import com.example.board.domain.post.dto.PostListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomPostDao {

    Page<PostListResponse> findPostList(Long categoryId, String keyword, Pageable pageable);

    PostDetailResponse findPost(Long postId);

}
