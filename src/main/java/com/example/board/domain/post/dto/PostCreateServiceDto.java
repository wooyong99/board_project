package com.example.board.domain.post.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class PostCreateServiceDto {

    private String title;

    private String content;

    private Long categoryId;

    @Builder
    public PostCreateServiceDto(String title, String content, Long categoryId) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
    }

}
