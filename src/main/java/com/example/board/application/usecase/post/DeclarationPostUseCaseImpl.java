package com.example.board.application.usecase.post;

import com.example.board.application.usecase.post.dto.DeclarationPostServiceDto;
import com.example.board.domain.entity.Post;
import com.example.board.domain.entity.RoleEnum;
import com.example.board.domain.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeclarationPostUseCaseImpl implements DeclarationPostUseCase {

    private final PostRepository postRepository;

    public DeclarationPostUseCaseImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    @Transactional
    public void declaration(DeclarationPostServiceDto dto) {
        Post post = postRepository.findById(dto.getPostId());
        post.delete();    // Soft Delete 방식 적용
        if (post.getMember().getRoles().equals(RoleEnum.USER)) {
            post.getMember().updateBlockedAt();
            post.getMember().updateBlockStatus(true);
        }
    }
}
