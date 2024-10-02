package com.example.board.application.usecase.post;

import com.example.board.application.usecase.post.dto.LikePostServiceDto;
import com.example.board.domain.entity.Post;
import com.example.board.domain.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikePostUseCaseImpl implements LikePostUseCase {

    private final PostRepository postRepository;

    public LikePostUseCaseImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    @Transactional
    public Long like(LikePostServiceDto dto) {
        Post post = postRepository.findById(dto.getPostId());
        return post.increaseLike();
    }
}
