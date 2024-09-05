package com.example.board.application.service.dto;

import com.example.board.adapter.ports.in.dto.request.comment.CommentCreateRequest;
import lombok.Data;

@Data
public class CommentCreateServiceDto {

    private String content;

    public CommentCreateServiceDto(CommentCreateRequest request) {
        this.content = request.getContent();
    }
}
