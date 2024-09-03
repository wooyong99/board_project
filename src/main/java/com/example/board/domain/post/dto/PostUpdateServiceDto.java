package com.example.board.domain.post.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class PostUpdateServiceDto {

    private String title;

    private String content;

    @Builder
    public PostUpdateServiceDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
