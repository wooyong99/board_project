package com.example.board.domain.inquiry.service;

import com.example.board.domain.inquiry.dao.InquiryDao;
import com.example.board.domain.inquiry.dto.InquiryCreateServiceDto;
import com.example.board.domain.inquiry.dto.InquiryDetailResponse;
import com.example.board.domain.inquiry.dto.InquiryListResponse;
import com.example.board.domain.inquiry.entity.Inquiry;
import com.example.board.domain.member.dao.MemberDao;
import com.example.board.domain.member.entity.Member;
import com.example.board.global.exception.DuplicateInquiryException;
import com.example.board.global.exception.NotFoundInquiryException;
import com.example.board.global.exception.NotFoundMemberException;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InquiryService {

    private final InquiryDao inquiryDao;
    private final MemberDao memberDao;
    private final PasswordEncoder encoder;
    private final EntityManager em;

    public InquiryService(InquiryDao inquiryDao, MemberDao memberDao, PasswordEncoder encoder,
        EntityManager em) {
        this.inquiryDao = inquiryDao;
        this.memberDao = memberDao;
        this.encoder = encoder;
        this.em = em;
    }

    @Transactional
    public void register(InquiryCreateServiceDto dto) {
        Member member = findMemberByEmail(dto.getEmail());
        if (!encoder.matches(dto.getPassword(), member.getPassword())) {
            throw new NotFoundMemberException("존재하지 않는 사용자입니다.");
        }
        if (!member.isBlock()) {
            throw new IllegalArgumentException("차단된 사용자가 아닙니다.");
        }
        if (inquiryDao.existsByMemberId(member.getId())) {
            throw new DuplicateInquiryException("이미 차단 해제 문의 내역이 존재합니다.");
        }
        Inquiry inquiry = Inquiry.builder()
            .content(dto.getContent())
            .member(member)
            .build();
        inquiryDao.save(inquiry);
    }

    @Transactional
    public void delete(Long inquiryId) {
        Inquiry inquiry = inquiryDao.findById(inquiryId).orElseThrow(
            () -> new NotFoundInquiryException("존재하지 않는 문의 내역입니다.")
        );
        inquiryDao.delete(inquiry);
        em.flush();
        inquiry.getMember().updateBlockStatus(false);
    }

    private Member findMemberByEmail(String email) {
        return memberDao.findByEmail(email).orElseThrow(
            () -> new NotFoundMemberException("존재하지 않는 사용자입니다.")
        );
    }

    public Page<InquiryListResponse> findList(Pageable pageable) {
        return inquiryDao.findList(pageable);
    }

    public InquiryDetailResponse findOne(Long id) {
        return inquiryDao.findById(id).orElseThrow(
            () -> new NotFoundInquiryException("존재하지 않는 문의입니다.")
        ).toDetailResponse();
    }
}
