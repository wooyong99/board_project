package com.example.board.application.usecase.comment;

import com.example.board.api.controller.comment.response.CommentListResponse;
import com.example.board.application.usecase.comment.dto.GetCommentListServiceRequest;
import com.example.board.domain.repository.CommentRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetCommentListUseCaseImpl implements GetCommentListUseCase {

    private final CommentRepository commentRepository;

    public GetCommentListUseCaseImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<CommentListResponse> findCommentByPost(GetCommentListServiceRequest dto) {
        return commentRepository.findCommentByPost(dto.getPostId());
    }
}
