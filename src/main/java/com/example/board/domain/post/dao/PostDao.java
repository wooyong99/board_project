package com.example.board.domain.post.dao;

import com.example.board.domain.post.dto.PostDetailResponse;
import com.example.board.domain.post.dto.PostListResponse;
import com.example.board.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostDao extends JpaRepository<Post, Long>, CustomPostDao {

    Page<Post> findByCategoryIdOrderByCreatedAtDesc(Long categoryId, Pageable pageable);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Override
    Page<PostListResponse> findPostList(Long categoryId, String keyword, Pageable pageable);

    @Override
    PostDetailResponse findPost(Long postId);
}
