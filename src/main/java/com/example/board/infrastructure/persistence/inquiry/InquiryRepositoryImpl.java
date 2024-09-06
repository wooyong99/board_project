package com.example.board.infrastructure.persistence.inquiry;

import com.example.board.api.controller.inquiry.response.InquiryListResponse;
import com.example.board.domain.entity.Inquiry;
import com.example.board.domain.repository.InquiryRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class InquiryRepositoryImpl implements InquiryRepository {

    private final InquiryJpaRepository inquiryJpaRepository;
    private final CustomInquiryRepository customInquiryRepository;

    public InquiryRepositoryImpl(InquiryJpaRepository inquiryJpaRepository,
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
