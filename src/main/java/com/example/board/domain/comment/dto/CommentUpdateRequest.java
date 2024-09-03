package com.example.board.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentUpdateRequest {

    @NotBlank(message = "댓글 내용은 공백, 빈칸을 입력할 수 없습니다.")
    private String content;

    public CommentUpdateRequest() {
    }

    public CommentUpdateRequest(String content) {
        this.content = content;
    }

    public CommentUpdateServiceDto toServiceDto() {
        return CommentUpdateServiceDto.builder()
            .content(this.content)
            .build();
    }
}
