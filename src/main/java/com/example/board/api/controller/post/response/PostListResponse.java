package com.example.board.api.controller.post.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PostListResponse {

    private Long id;

    private String title;

    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdAt;

    private Long commentSize;

    public PostListResponse() {
    }

    @QueryProjection
    public PostListResponse(Long id, String title, String content, LocalDateTime createdAt,
        Long commentSize) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.commentSize = commentSize;
    }
}
