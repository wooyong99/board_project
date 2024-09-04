package com.example.board.persistence.post.dao;

import com.example.board.persistence.post.dto.PostDetailResponse;
import com.example.board.persistence.post.dto.PostListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomPostDao {

    Page<PostListResponse> findPostList(Long categoryId, String keyword, Pageable pageable);

    PostDetailResponse findPost(Long postId);

}
