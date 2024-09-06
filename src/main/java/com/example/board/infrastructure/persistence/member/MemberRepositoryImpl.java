package com.example.board.infrastructure.persistence.member;

import com.example.board.api.controller.member.response.MemberInfoResponse;
import com.example.board.domain.entity.Member;
import com.example.board.domain.repository.MemberRepository;
import com.example.board.exception.NotFoundMemberException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;
    private final CustomMemberRepository customMemberRepository;

    public MemberRepositoryImpl(MemberJpaRepository memberJpaRepository,
        CustomMemberRepository customMemberRepository) {
        this.memberJpaRepository = memberJpaRepository;
        this.customMemberRepository = customMemberRepository;
    }

    @Override
    public void save(Member member) {
        memberJpaRepository.save(member);
    }

    @Override
    public Member findById(Long memberId) {
        return memberJpaRepository.findById(memberId).orElseThrow(
            () -> new NotFoundMemberException("존재하지 않는 사용자입니다.")
        );
    }

    @Override
    public Member findByEmailAndIsDeletedFalse(String email) {
        return memberJpaRepository.findByEmailAndIsDeletedFalse(email).orElseThrow(
            () -> new NotFoundMemberException("존재하지 않는 사용자입니다.")
        );
    }

    public Page<MemberInfoResponse> search(String keyword, Pageable pageable) {
        return customMemberRepository.search(keyword, pageable);
    }

    public boolean existsByEmailAndIsDeletedFalse(String email) {
        return memberJpaRepository.existsByEmailAndIsDeletedFalse(email);
    }
}
