package com.example.board.application.service.dto;

import com.example.board.adapter.ports.in.dto.request.comment.CommentUpdateRequest;
import lombok.Data;

@Data
public class CommentUpdateServiceDto {

    private String content;

    public CommentUpdateServiceDto(CommentUpdateRequest request) {
        this.content = request.getContent();
    }
}
