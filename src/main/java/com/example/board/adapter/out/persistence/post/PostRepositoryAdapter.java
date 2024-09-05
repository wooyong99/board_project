package com.example.board.adapter.out.persistence.post;

import com.example.board.adapter.ports.in.dto.response.post.PostDetailResponse;
import com.example.board.adapter.ports.in.dto.response.post.PostListResponse;
import com.example.board.domain.entity.Post;
import com.example.board.infrastructure.exception.NotFoundPostException;
import com.example.board.infrastructure.persistence.post.CustomPostRepository;
import com.example.board.infrastructure.persistence.post.PostJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PostRepositoryAdapter {

    private final PostJpaRepository postJpaRepository;

    private final CustomPostRepository customPostRepository;

    @Autowired
    public PostRepositoryAdapter(PostJpaRepository postJpaRepository,
        CustomPostRepository customPostRepository) {
        this.postJpaRepository = postJpaRepository;
        this.customPostRepository = customPostRepository;
    }

    public Post findById(Long postId) {
        return postJpaRepository.findById(postId).orElseThrow(
            () -> new NotFoundPostException("존재하지 않는 게시글입니다.")
        );
    }

    public void save(Post post) {
        postJpaRepository.save(post);
    }

    public PostDetailResponse findPost(Long postId) {
        return customPostRepository.findPost(postId);
    }

    public Page<PostListResponse> findPostList(Long categoryId, String keyword, Pageable pageable) {
        return customPostRepository.findPostList(categoryId, keyword, pageable);
    }
}
