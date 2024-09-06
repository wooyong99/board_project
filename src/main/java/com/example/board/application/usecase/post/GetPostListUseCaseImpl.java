package com.example.board.application.usecase.post;

import com.example.board.api.controller.post.response.PostListResponse;
import com.example.board.application.usecase.post.dto.GetPostListServiceDto;
import com.example.board.domain.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class GetPostListUseCaseImpl implements GetPostListUseCase {

    private final PostRepository postRepository;

    public GetPostListUseCaseImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Page<PostListResponse> getPostList(GetPostListServiceDto dto) {
        return postRepository.findPostList(dto.getCategoryId(), null, dto.getPageable());
    }
}
