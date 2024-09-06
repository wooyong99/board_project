package com.example.board.application.usecase.inquiry;

import com.example.board.api.controller.inquiry.response.InquiryListResponse;
import com.example.board.application.usecase.inquiry.dto.GetInquiryListServiceDto;
import org.springframework.data.domain.Page;

public interface GetInquiryListUseCase {

    Page<InquiryListResponse> findList(GetInquiryListServiceDto dto);
}
