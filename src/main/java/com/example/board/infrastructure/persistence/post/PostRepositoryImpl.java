package com.example.board.infrastructure.persistence.post;

import com.example.board.api.controller.post.response.PostDetailResponse;
import com.example.board.api.controller.post.response.PostListResponse;
import com.example.board.domain.entity.Post;
import com.example.board.domain.repository.PostRepository;
import com.example.board.exception.NotFoundPostException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;
    private final CustomPostRepository customPostRepository;

    public PostRepositoryImpl(PostJpaRepository postJpaRepository,
        CustomPostRepository customPostRepository) {
        this.postJpaRepository = postJpaRepository;
        this.customPostRepository = customPostRepository;
    }

    public Post findById(Long postId) {
        return postJpaRepository.findById(postId).orElseThrow(
            () -> new NotFoundPostException("존재하지 않는 게시글입니다.")
        );
    }

    @Override
    public Page<PostListResponse> findPostList(Long categoryId, String keyword, Pageable pageable) {
        return customPostRepository.findPostList(categoryId, keyword, pageable);
    }

    public void save(Post post) {
        postJpaRepository.save(post);
    }

    public PostDetailResponse findPost(Long postId) {
        return customPostRepository.findPost(postId);
    }
}
