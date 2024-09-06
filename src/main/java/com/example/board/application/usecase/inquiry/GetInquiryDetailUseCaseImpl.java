package com.example.board.application.usecase.inquiry;

import com.example.board.api.controller.inquiry.response.InquiryDetailResponse;
import com.example.board.application.usecase.inquiry.dto.GetInquiryDetailServiceDto;
import com.example.board.domain.repository.InquiryRepository;
import com.example.board.exception.NotFoundInquiryException;
import org.springframework.stereotype.Service;

@Service
public class GetInquiryDetailUseCaseImpl implements GetInquiryDetailUseCase {

    private final InquiryRepository inquiryRepository;

    public GetInquiryDetailUseCaseImpl(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    @Override
    public InquiryDetailResponse findOne(GetInquiryDetailServiceDto dto) {
        return inquiryRepository.findByIdAndIsDeletedFalse(dto.getInquiryId()).orElseThrow(
            () -> new NotFoundInquiryException("존재하지 않는 문의입니다.")
        ).toDetailResponse();
    }
}
