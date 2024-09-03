package com.example.board.domain.comment.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CommentCreateServiceDto {

    private String content;

    @Builder
    public CommentCreateServiceDto(String content) {
        this.content = content;
    }
}
