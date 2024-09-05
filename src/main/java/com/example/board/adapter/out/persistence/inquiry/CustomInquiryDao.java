package com.example.board.adapter.ports.out.persistence.inquiry;

import com.example.board.adapter.ports.in.dto.response.post.inquiry.InquiryListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomInquiryDao {

    Page<InquiryListResponse> findList(Pageable pageable);

}
