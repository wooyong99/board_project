package com.example.board.application.usecase.inquiry;

import com.example.board.api.controller.inquiry.response.InquiryDetailResponse;
import com.example.board.application.usecase.inquiry.dto.GetInquiryDetailServiceDto;
import org.springframework.stereotype.Service;

@Service
public interface GetInquiryDetailUseCase {

    InquiryDetailResponse findOne(GetInquiryDetailServiceDto dto);
}
