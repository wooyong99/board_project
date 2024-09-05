package com.example.board.application.port.in.inquiry;

import com.example.board.adapter.ports.in.dto.response.post.inquiry.InquiryDetailResponse;

public interface GetInquiryDetailUseCase {

    InquiryDetailResponse findOne(Long id);
}
