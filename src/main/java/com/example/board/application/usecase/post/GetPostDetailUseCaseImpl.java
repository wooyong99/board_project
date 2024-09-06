package com.example.board.application.usecase.post;

import com.example.board.api.controller.post.response.PostDetailResponse;
import com.example.board.application.usecase.post.dto.GetPostDetailServiceDto;
import com.example.board.domain.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class GetPostDetailUseCaseImpl implements GetPostDetailUseCase {

    private final PostRepository postRepository;

    public GetPostDetailUseCaseImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDetailResponse getPost(GetPostDetailServiceDto dto) {
        return postRepository.findPost(dto.getPostId());
    }
}
