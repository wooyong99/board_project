package com.example.board.application.usecase.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeletePostServiceDto {

    private Long postId;
    private Long memberId;
}
