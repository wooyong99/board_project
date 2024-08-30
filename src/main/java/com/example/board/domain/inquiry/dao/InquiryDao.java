package com.example.board.domain.inquiry.dao;

import com.example.board.domain.inquiry.entity.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryDao extends JpaRepository<Inquiry, Long> {

    boolean existsByMemberId(Long id);

    Page<Inquiry> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
