package com.example.board.domain.post.dto;

import com.example.board.domain.comment.entity.Comment;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostDetailResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Long likes;
    private String category;
    private String author;
    private List<Comment> comments;

    @QueryProjection
    public PostDetailResponse(Long id, String title, String content, LocalDateTime createdAt,
        Long likes, String category, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.likes = likes;
        this.category = category;
        this.author = author;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
