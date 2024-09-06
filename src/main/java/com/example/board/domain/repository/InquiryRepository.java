package com.example.board.domain.repository;

import com.example.board.api.controller.inquiry.response.InquiryListResponse;
import com.example.board.domain.entity.Inquiry;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InquiryRepository {

    void save(Inquiry inquiry);

    boolean existsByMemberIdAndIsDeletedFalse(Long id);

    Optional<Inquiry> findByIdAndIsDeletedFalse(Long inquiryId);

    Page<InquiryListResponse> findList(Pageable pageable);
}
