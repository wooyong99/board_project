package com.example.board.application.service.dto;

import com.example.board.adapter.ports.in.dto.request.post.PostUpdateRequest;
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
