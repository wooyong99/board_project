package com.example.board.domain.post.dao;

import com.example.board.domain.post.dto.PostListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomPostDao {

    Page<PostListResponse> findPostList(Long categoryId, Pageable pageable);

}
