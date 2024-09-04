package com.example.board.persistence.post.dto;

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
