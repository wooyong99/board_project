package com.example.board.domain.repository;

import com.example.board.domain.entity.Comment;

public interface CommentRepository {

    void save(Comment comment);

    Comment findByIdAndPostId(Long commentId, Long postId);
}
