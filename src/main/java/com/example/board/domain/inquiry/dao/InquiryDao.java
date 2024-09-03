package com.example.board.domain.inquiry.dao;

import com.example.board.domain.inquiry.dto.InquiryListResponse;
import com.example.board.domain.inquiry.entity.Inquiry;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryDao extends JpaRepository<Inquiry, Long>, CustomInquiryDao {

    boolean existsByMemberIdAndIsDeletedFalse(Long id);

    Page<InquiryListResponse> findList(Pageable pageable);

    Optional<Inquiry> findByIdAndIsDeletedFalse(Long inquiryId);
}
