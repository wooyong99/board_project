package com.example.board.application.usecase.inquiry;

import com.example.board.application.usecase.inquiry.dto.SaveInquiryServiceDto;
import com.example.board.domain.entity.Inquiry;
import com.example.board.domain.entity.Member;
import com.example.board.domain.repository.InquiryRepository;
import com.example.board.domain.repository.MemberRepository;
import com.example.board.exception.DuplicateInquiryException;
import com.example.board.exception.NotFoundMemberException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaveInquiryUseCaseImpl implements SaveInquiryUseCase {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final InquiryRepository inquiryRepository;

    public SaveInquiryUseCaseImpl(MemberRepository memberRepository, PasswordEncoder encoder,
        InquiryRepository inquiryRepository) {
        this.memberRepository = memberRepository;
        this.encoder = encoder;
        this.inquiryRepository = inquiryRepository;
    }

    @Override
    @Transactional
    public void save(SaveInquiryServiceDto dto) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(dto.getEmail());
        if (!encoder.matches(dto.getPassword(), member.getPassword())) {
            throw new NotFoundMemberException("존재하지 않는 사용자입니다.");
        }
        if (!member.isBlock()) {
            throw new IllegalArgumentException("차단된 사용자가 아닙니다.");
        }
        if (inquiryRepository.existsByMemberIdAndIsDeletedFalse(member.getId())) {
            throw new DuplicateInquiryException("이미 차단 해제 문의 내역이 존재합니다.");
        }
        Inquiry inquiry = Inquiry.create(dto.getContent(), member);

        inquiryRepository.save(inquiry);
    }
}
