package com.example.board.adapter.out.comment;

import com.example.board.domain.entity.Comment;
import com.example.board.infrastructure.exception.NotFoundCommentException;
import com.example.board.infrastructure.persistence.comment.CommentJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentRepositoryAdapter {

    private final CommentJpaRepository commentJpaRepository;

    @Autowired
    public CommentRepositoryAdapter(CommentJpaRepository commentJpaRepository) {
        this.commentJpaRepository = commentJpaRepository;
    }

    public void save(Comment comment) {
        commentJpaRepository.save(comment);
    }

    public Comment findByIdAndPostId(Long commentId, Long postId) {
        return commentJpaRepository.findByIdAndPostId(commentId, postId).orElseThrow(
            () -> new NotFoundCommentException("존재하지 않는 댓글입니다.")
        );
    }
}
