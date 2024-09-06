package com.example.board.application.usecase.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
public class SearchPostServiceDto {

    private String keyword;
    private Long categoryId;
    private Pageable pageable;

}
