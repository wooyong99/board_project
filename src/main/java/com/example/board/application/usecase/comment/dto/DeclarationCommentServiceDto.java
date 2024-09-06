package com.example.board.application.usecase.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeclarationCommentServiceDto {

    private Long postId;
    private Long commentId;

}
