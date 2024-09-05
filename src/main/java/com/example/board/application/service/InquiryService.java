package com.example.board.application.service;

import com.example.board.adapter.ports.in.dto.response.post.inquiry.InquiryDetailResponse;
import com.example.board.adapter.ports.in.dto.response.post.inquiry.InquiryListResponse;
import com.example.board.adapter.ports.out.persistence.inquiry.InquiryDao;
import com.example.board.adapter.ports.out.persistence.member.MemberDao;
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

    private final InquiryDao inquiryDao;
    private final MemberDao memberDao;
    private final PasswordEncoder encoder;
    private final EntityManager em;

    @Autowired
    public InquiryService(InquiryDao inquiryDao, MemberDao memberDao, PasswordEncoder encoder,
        EntityManager em) {
        this.inquiryDao = inquiryDao;
        this.memberDao = memberDao;
        this.encoder = encoder;
        this.em = em;
    }

    @Transactional
    @Override
    public void save(InquiryCreateServiceDto dto) {
        Member member = findMemberByEmail(dto.getEmail());
        if (!encoder.matches(dto.getPassword(), member.getPassword())) {
            throw new NotFoundMemberException("존재하지 않는 사용자입니다.");
        }
        if (!member.isBlock()) {
            throw new IllegalArgumentException("차단된 사용자가 아닙니다.");
        }
        if (inquiryDao.existsByMemberIdAndIsDeletedFalse(member.getId())) {
            throw new DuplicateInquiryException("이미 차단 해제 문의 내역이 존재합니다.");
        }
        Inquiry inquiry = Inquiry.create(dto.getContent(), member);

        inquiryDao.save(inquiry);
    }

    @Transactional
    @Override
    public void delete(Long inquiryId) {
        Inquiry inquiry = inquiryDao.findByIdAndIsDeletedFalse(inquiryId).orElseThrow(
            () -> new NotFoundInquiryException("존재하지 않는 문의 내역입니다.")
        );
        inquiry.delete();     // Soft Delete 방식 적용
        em.flush();
        inquiry.getMember().updateBlockStatus(false);
    }

    private Member findMemberByEmail(String email) {
        return memberDao.findByEmailAndIsDeletedFalse(email).orElseThrow(
            () -> new NotFoundMemberException("존재하지 않는 사용자입니다.")
        );
    }

    @Transactional
    @Override
    public Page<InquiryListResponse> findList(Pageable pageable) {
        return inquiryDao.findList(pageable);
    }

    @Transactional
    @Override
    public InquiryDetailResponse findOne(Long id) {
        return inquiryDao.findByIdAndIsDeletedFalse(id).orElseThrow(
            () -> new NotFoundInquiryException("존재하지 않는 문의입니다.")
        ).toDetailResponse();
    }
}
