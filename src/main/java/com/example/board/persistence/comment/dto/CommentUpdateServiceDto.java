package com.example.board.persistence.comment.dto;

import lombok.Data;

@Data
public class CommentUpdateServiceDto {

    private String content;

    public CommentUpdateServiceDto(CommentUpdateRequest request) {
        this.content = request.getContent();
    }
}
