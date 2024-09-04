package com.example.board.persistence.inquiry.dao;

import com.example.board.persistence.inquiry.dto.InquiryListResponse;
import com.example.board.persistence.inquiry.entity.Inquiry;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryDao extends JpaRepository<Inquiry, Long>, CustomInquiryDao {

    boolean existsByMemberIdAndIsDeletedFalse(Long id);

    Page<InquiryListResponse> findList(Pageable pageable);

    Optional<Inquiry> findByIdAndIsDeletedFalse(Long inquiryId);
}
