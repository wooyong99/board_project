package com.example.board.domain.post.dto;

import lombok.Data;

@Data
public class PostUpdateServiceDto {

    private String title;

    private String content;

    public PostUpdateServiceDto(PostUpdateRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }
}
