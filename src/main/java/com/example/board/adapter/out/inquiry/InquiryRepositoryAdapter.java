package com.example.board.adapter.out.inquiry;

import com.example.board.adapter.ports.in.dto.response.post.inquiry.InquiryListResponse;
import com.example.board.domain.entity.Inquiry;
import com.example.board.infrastructure.persistence.inquiry.CustomInquiryRepository;
import com.example.board.infrastructure.persistence.inquiry.InquiryJpaRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class InquiryRepositoryAdapter {

    private final InquiryJpaRepository inquiryJpaRepository;
    private final CustomInquiryRepository customInquiryRepository;

    @Autowired
    public InquiryRepositoryAdapter(InquiryJpaRepository inquiryJpaRepository,
        CustomInquiryRepository customInquiryRepository) {
        this.inquiryJpaRepository = inquiryJpaRepository;
        this.customInquiryRepository = customInquiryRepository;
    }

    public boolean existsByMemberIdAndIsDeletedFalse(Long id) {
        return inquiryJpaRepository.existsByMemberIdAndIsDeletedFalse(id);
    }

    public Optional<Inquiry> findByIdAndIsDeletedFalse(Long inquiryId) {
        return inquiryJpaRepository.findByIdAndIsDeletedFalse(inquiryId);
    }

    public Page<InquiryListResponse> findList(Pageable pageable) {
        return customInquiryRepository.findList(pageable);
    }

    public void save(Inquiry inquiry) {
        inquiryJpaRepository.save(inquiry);
    }
}
