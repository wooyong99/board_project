package com.example.board.domain.repository;

import com.example.board.api.controller.comment.response.CommentListResponse;
import com.example.board.domain.entity.Comment;
import java.util.List;

public interface CommentRepository {

    void save(Comment comment);

    Comment findByIdAndPostId(Long commentId, Long postId);

    List<CommentListResponse> findCommentByPost(Long postId);
}
