package com.example.board.application.usecase.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
public class SearchMemberServiceDto {

    private String keyword;
    private Pageable pageable;
    
}
