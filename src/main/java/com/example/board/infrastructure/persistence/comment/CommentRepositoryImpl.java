package com.example.board.infrastructure.persistence.comment;

import com.example.board.api.controller.comment.response.CommentListResponse;
import com.example.board.domain.entity.Comment;
import com.example.board.domain.repository.CommentRepository;
import com.example.board.exception.NotFoundCommentException;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;
    private final CustomCommentRepository customCommentRepository;

    public CommentRepositoryImpl(CommentJpaRepository commentJpaRepository,
        CustomCommentRepository customCommentRepository) {
        this.commentJpaRepository = commentJpaRepository;
        this.customCommentRepository = customCommentRepository;
    }

    @Override
    public void save(Comment comment) {
        commentJpaRepository.save(comment);
    }

    public Comment findByIdAndPostId(Long commentId, Long postId) {
        return commentJpaRepository.findByIdAndPostId(commentId, postId).orElseThrow(
            () -> new NotFoundCommentException("존재하지 않는 댓글입니다.")
        );
    }

    @Override
    public List<CommentListResponse> findCommentByPost(Long postId) {
        return customCommentRepository.findCommentByPost(postId);
    }
}
