package com.example.board.application.usecase.post;

import com.example.board.api.controller.post.response.PostListResponse;
import com.example.board.application.usecase.post.dto.SearchPostServiceDto;
import com.example.board.domain.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SearchPostUseCaseImpl implements SearchPostUseCase {

    private final PostRepository postRepository;

    public SearchPostUseCaseImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Page<PostListResponse> search(SearchPostServiceDto dto) {
        return postRepository.findPostList(dto.getCategoryId(), dto.getKeyword(),
            dto.getPageable());
    }
}
