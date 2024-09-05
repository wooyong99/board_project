package com.example.board.application.port.in.inquiry;

import com.example.board.application.service.dto.InquiryCreateServiceDto;

public interface SaveInquiryUseCase {

    void save(InquiryCreateServiceDto dto);
}
