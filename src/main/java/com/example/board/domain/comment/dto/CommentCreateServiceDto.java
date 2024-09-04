package com.example.board.domain.comment.dto;

import lombok.Data;

@Data
public class CommentCreateServiceDto {

    private String content;

    public CommentCreateServiceDto(CommentCreateRequest request) {
        this.content = request.getContent();
    }
}
