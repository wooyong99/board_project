package com.example.board.infrastructure.persistence.inquiry;

import com.example.board.adapter.ports.in.dto.response.post.inquiry.InquiryListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomInquiryRepository {

    Page<InquiryListResponse> findList(Pageable pageable);

}
