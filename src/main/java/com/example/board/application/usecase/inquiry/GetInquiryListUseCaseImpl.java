package com.example.board.application.usecase.inquiry;

import com.example.board.api.controller.inquiry.response.InquiryListResponse;
import com.example.board.application.usecase.inquiry.dto.GetInquiryListServiceDto;
import com.example.board.domain.repository.InquiryRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class GetInquiryListUseCaseImpl implements GetInquiryListUseCase {

    private final InquiryRepository inquiryRepository;

    public GetInquiryListUseCaseImpl(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    @Override
    public Page<InquiryListResponse> findList(GetInquiryListServiceDto dto) {
        return inquiryRepository.findList(dto.getPageable());
    }
}
