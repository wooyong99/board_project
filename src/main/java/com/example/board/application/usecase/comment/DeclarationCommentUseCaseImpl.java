package com.example.board.application.usecase.comment;

import com.example.board.application.usecase.comment.dto.DeclarationCommentServiceDto;
import com.example.board.domain.entity.Comment;
import com.example.board.domain.entity.RoleEnum;
import com.example.board.domain.repository.CommentRepository;
import org.springframework.stereotype.Service;


@Service
public class DeclarationCommentUseCaseImpl implements DeclarationCommentUseCase {

    private final CommentRepository commentRepository;

    public DeclarationCommentUseCaseImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void declaration(DeclarationCommentServiceDto dto) {
        Comment comment = commentRepository.findByIdAndPostId(dto.getCommentId(), dto.getPostId());
        comment.delete();     // Soft Delete 방식 적용
        if (comment.getMember().getRoles().equals(RoleEnum.USER)) {
            comment.getMember().updateBlockedAt();
            comment.getMember().updateBlockStatus(true);
        }
    }
}
