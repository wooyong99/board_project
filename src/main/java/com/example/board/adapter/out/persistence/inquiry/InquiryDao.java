package com.example.board.adapter.ports.out.persistence.inquiry;

import com.example.board.adapter.ports.in.dto.response.post.inquiry.InquiryListResponse;
import com.example.board.domain.entity.Inquiry;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryDao extends JpaRepository<Inquiry, Long>, CustomInquiryDao {

    boolean existsByMemberIdAndIsDeletedFalse(Long id);

    Page<InquiryListResponse> findList(Pageable pageable);

    Optional<Inquiry> findByIdAndIsDeletedFalse(Long inquiryId);
}
