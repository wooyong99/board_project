package com.example.board.application.usecase.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateCommentServiceDto {

    private Long postId;
    private Long commentId;
    private Long memberId;
    private String content;

}
