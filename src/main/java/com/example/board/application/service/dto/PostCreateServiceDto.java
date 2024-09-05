package com.example.board.application.service.dto;

import com.example.board.adapter.ports.in.dto.request.post.PostCreateRequest;
import lombok.Data;

@Data
public class PostCreateServiceDto {

    private String title;

    private String content;

    private Long categoryId;

    public PostCreateServiceDto(PostCreateRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.categoryId = request.getCategoryId();
    }
}
