package com.example.board.application.usecase.inquiry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
public class GetInquiryListServiceDto {

    private Pageable pageable;

}
