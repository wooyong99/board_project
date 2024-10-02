package com.example.board.api.controller.comment.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class CommentListResponse {

    private Long commentId;
    private String content;
    private String author;

    public CommentListResponse() {
    }

    @QueryProjection
    public CommentListResponse(Long commentId, String content, String author) {
        this.commentId = commentId;
        this.content = content;
        this.author = author;
    }

}
