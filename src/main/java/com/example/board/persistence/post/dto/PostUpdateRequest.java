package com.example.board.persistence.post.dto;

import lombok.Data;

@Data
public class PostUpdateRequest {

    private String title;

    private String content;

    public PostUpdateRequest() {
    }

    public PostUpdateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
