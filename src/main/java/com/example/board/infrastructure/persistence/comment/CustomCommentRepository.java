package com.example.board.infrastructure.persistence.comment;

import com.example.board.api.controller.comment.response.CommentListResponse;
import java.util.List;

public interface CustomCommentRepository {

    List<CommentListResponse> findCommentByPost(Long postId);
}
