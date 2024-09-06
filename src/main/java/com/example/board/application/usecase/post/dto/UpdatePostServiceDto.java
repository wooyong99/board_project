package com.example.board.application.usecase.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatePostServiceDto {

    private String title;
    private String content;
    private Long postId;
    private String email;
    
}
