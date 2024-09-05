package com.example.board.application.service;

import com.example.board.adapter.out.persistence.inquiry.InquiryRepositoryAdapter;
import com.example.board.adapter.out.persistence.member.MemberRepositoryAdapter;
import com.example.board.adapter.ports.in.dto.response.post.inquiry.InquiryDetailResponse;
import com.example.board.adapter.ports.in.dto.response.post.inquiry.InquiryListResponse;
import com.example.board.application.port.in.inquiry.DeleteInquiryUseCase;
import com.example.board.application.port.in.inquiry.GetInquiryDetailUseCase;
import com.example.board.application.port.in.inquiry.GetInquiryListUseCase;
import com.example.board.application.port.in.inquiry.SaveInquiryUseCase;
import com.example.board.application.service.dto.InquiryCreateServiceDto;
import com.example.board.domain.entity.Inquiry;
import com.example.board.domain.entity.Member;
import com.example.board.infrastructure.exception.DuplicateInquiryException;
import com.example.board.infrastructure.exception.NotFoundInquiryException;
import com.example.board.infrastructure.exception.NotFoundMemberException;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InquiryService implements SaveInquiryUseCase, DeleteInquiryUseCase,
    GetInquiryListUseCase, GetInquiryDetailUseCase {

    private final InquiryRepositoryAdapter inquiryRepositoryAdapter;
    private final MemberRepositoryAdapter memberRepositoryAdapter;
    private final PasswordEncoder encoder;
    private final EntityManager em;

    @Autowired
    public InquiryService(InquiryRepositoryAdapter inquiryRepositoryAdapter,
        MemberRepositoryAdapter memberRepositoryAdapter, PasswordEncoder encoder,
        EntityManager em) {
        this.inquiryRepositoryAdapter = inquiryRepositoryAdapter;
        this.memberRepositoryAdapter = memberRepositoryAdapter;
        this.encoder = encoder;
        this.em = em;
    }

    @Transactional
    @Override
    public void save(InquiryCreateServiceDto dto) {
        Member member = memberRepositoryAdapter.findByEmailAndIsDeletedFalse(dto.getEmail());
        if (!encoder.matches(dto.getPassword(), member.getPassword())) {
            throw new NotFoundMemberException("존재하지 않는 사용자입니다.");
        }
        if (!member.isBlock()) {
            throw new IllegalArgumentException("차단된 사용자가 아닙니다.");
        }
        if (inquiryRepositoryAdapter.existsByMemberIdAndIsDeletedFalse(member.getId())) {
            throw new DuplicateInquiryException("이미 차단 해제 문의 내역이 존재합니다.");
        }
        Inquiry inquiry = Inquiry.create(dto.getContent(), member);

        inquiryRepositoryAdapter.save(inquiry);
    }

    @Transactional
    @Override
    public void delete(Long inquiryId) {
        Inquiry inquiry = inquiryRepositoryAdapter.findByIdAndIsDeletedFalse(inquiryId).orElseThrow(
            () -> new NotFoundInquiryException("존재하지 않는 문의 내역입니다.")
        );
        inquiry.delete();     // Soft Delete 방식 적용
        em.flush();
        inquiry.getMember().updateBlockStatus(false);
    }

    @Transactional
    @Override
    public Page<InquiryListResponse> findList(Pageable pageable) {
        return inquiryRepositoryAdapter.findList(pageable);
    }

    @Transactional
    @Override
    public InquiryDetailResponse findOne(Long id) {
        return inquiryRepositoryAdapter.findByIdAndIsDeletedFalse(id).orElseThrow(
            () -> new NotFoundInquiryException("존재하지 않는 문의입니다.")
        ).toDetailResponse();
    }
}
