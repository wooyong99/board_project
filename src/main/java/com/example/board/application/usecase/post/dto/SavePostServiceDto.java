package com.example.board.application.usecase.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SavePostServiceDto {

    private String title;
    private String content;
    private Long categoryId;
    private Long memberId;

}
