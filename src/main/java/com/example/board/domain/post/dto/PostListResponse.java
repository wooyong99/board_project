package com.example.board.domain.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostListResponse {

    private Long id;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private Integer commentSize;

    @QueryProjection
    public PostListResponse(Long id, String title, String content, LocalDateTime createdAt,
        Integer commentSize) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.commentSize = commentSize;
    }
}
