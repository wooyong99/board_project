package com.example.board.adapter.out.member;

import com.example.board.adapter.ports.in.dto.response.member.MemberInfoResponse;
import com.example.board.domain.entity.Member;
import com.example.board.infrastructure.exception.NotFoundMemberException;
import com.example.board.infrastructure.persistence.member.CustomMemberRepository;
import com.example.board.infrastructure.persistence.member.MemberJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class MemberRepositoryAdapter {

    private final MemberJpaRepository memberJpaRepository;

    private final CustomMemberRepository customMemberRepository;

    @Autowired
    public MemberRepositoryAdapter(MemberJpaRepository memberJpaRepository,
        CustomMemberRepository customMemberRepository) {
        this.memberJpaRepository = memberJpaRepository;
        this.customMemberRepository = customMemberRepository;
    }

    public Member findByEmailAndIsDeletedFalse(String email) {
        return memberJpaRepository.findByEmailAndIsDeletedFalse(email).orElseThrow(
            () -> new NotFoundMemberException("존재하지 않는 사용자입니다.")
        );
    }

    public void save(Member member) {
        memberJpaRepository.save(member);
    }

    public Page<MemberInfoResponse> search(String keyword, Pageable pageable) {
        return customMemberRepository.search(keyword, pageable);
    }

    public Member findById(Long memberId) {
        return memberJpaRepository.findById(memberId).orElseThrow(
            () -> new NotFoundMemberException("존재하지 않는 사용자입니다.")
        );
    }
}
