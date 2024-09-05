package com.example.board.application.port.in.inquiry;

import com.example.board.adapter.ports.in.dto.response.post.inquiry.InquiryListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetInquiryListUseCase {

    Page<InquiryListResponse> findList(Pageable pageable);
}
