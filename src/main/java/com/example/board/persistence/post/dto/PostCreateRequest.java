package com.example.board.persistence.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreateRequest {

    private String title;

    private String content;

    private Long categoryId;

    public PostCreateRequest() {
    }

    public PostCreateRequest(String title, String content, Long categoryId) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
    }
}
