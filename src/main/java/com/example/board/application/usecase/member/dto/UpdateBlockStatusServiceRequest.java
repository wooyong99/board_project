package com.example.board.application.usecase.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateBlockStatusServiceRequest {

    private Long memberId;
    private boolean isBlock;

}
