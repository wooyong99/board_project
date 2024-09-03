package com.example.board.domain.comment.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CommentUpdateServiceDto {

    private String content;

    @Builder
    public CommentUpdateServiceDto(String content) {
        this.content = content;
    }
}
