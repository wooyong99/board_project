package com.example.board.application.usecase.inquiry;

import com.example.board.application.usecase.inquiry.dto.DeleteInquiryServiceDto;
import com.example.board.domain.entity.Inquiry;
import com.example.board.domain.repository.InquiryRepository;
import com.example.board.exception.NotFoundInquiryException;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteInquiryUseCaseImpl implements DeleteInquiryUseCase {

    private final InquiryRepository inquiryRepository;
    private final EntityManager em;

    public DeleteInquiryUseCaseImpl(InquiryRepository inquiryRepository, EntityManager em) {
        this.inquiryRepository = inquiryRepository;
        this.em = em;
    }

    @Override
    @Transactional
    public void delete(DeleteInquiryServiceDto dto) {
        Inquiry inquiry = inquiryRepository.findByIdAndIsDeletedFalse(dto.getInquiryId())
            .orElseThrow(
                () -> new NotFoundInquiryException("존재하지 않는 문의 내역입니다.")
            );
        inquiry.delete();     // Soft Delete 방식 적용
        em.flush();
        inquiry.getMember().updateBlockStatus(false);
    }
}
