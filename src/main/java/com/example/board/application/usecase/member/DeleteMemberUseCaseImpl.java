package com.example.board.application.usecase.member;

import com.example.board.application.usecase.member.dto.DeleteMemberServiceRequest;
import com.example.board.domain.entity.Member;
import com.example.board.domain.repository.MemberRepository;
import com.example.board.infrastructure.utils.HttpServletUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteMemberUseCaseImpl implements DeleteMemberUseCase {

    private final MemberRepository memberRepository;
    private final HttpServletUtils servletUtils;

    public DeleteMemberUseCaseImpl(MemberRepository memberRepository,
        HttpServletUtils servletUtils) {
        this.memberRepository = memberRepository;
        this.servletUtils = servletUtils;
    }

    @Override
    @Transactional
    public void delete(DeleteMemberServiceRequest dto) {
        servletUtils.removeCookie(dto.getRequest(), dto.getResponse(), "RefreshToken");
        Member member = memberRepository.findByIdAndIsDeletedFalse(dto.getMemberId());
        member.delete();  // Soft Delete 방법 적용
    }

}
