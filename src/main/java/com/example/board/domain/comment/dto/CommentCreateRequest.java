package com.example.board.domain.comment.dto;

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

    public CommentCreateServiceDto toServiceDto() {
        return CommentCreateServiceDto.builder()
            .content(this.content)
            .build();
    }
}
