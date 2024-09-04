package com.example.board.persistence.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentCreateRequest {

    @NotBlank(message = "댓글 내용은 공백, 빈칸을 입력할 수 없습니다.")
    private String content;

    public CommentCreateRequest() {
    }

    public CommentCreateRequest(String content) {
        this.content = content;
    }
}
