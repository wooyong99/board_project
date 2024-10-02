package com.example.board.application.usecase.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveCommentServiceDto {

    private Long memberId;
    private Long postId;
    private String content;

}
