package com.example.board.persistence.inquiry.dao;

import com.example.board.persistence.inquiry.dto.InquiryListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomInquiryDao {

    Page<InquiryListResponse> findList(Pageable pageable);

}
