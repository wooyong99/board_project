package com.example.board.application.usecase.comment;

import com.example.board.application.usecase.comment.dto.UpdateCommentServiceDto;
import com.example.board.domain.entity.Comment;
import com.example.board.domain.entity.Member;
import com.example.board.domain.entity.RoleEnum;
import com.example.board.domain.repository.CommentRepository;
import com.example.board.domain.repository.MemberRepository;
import com.example.board.exception.AuthorizationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class UpdateCommentUseCaseImpl implements UpdateCommentUseCase {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    public UpdateCommentUseCaseImpl(MemberRepository memberRepository,
        CommentRepository commentRepository) {
        this.memberRepository = memberRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public void update(UpdateCommentServiceDto dto) {
        Member member = memberRepository.findByIdAndIsDeletedFalse(dto.getMemberId());
        Comment comment = commentRepository.findByIdAndPostId(dto.getCommentId(), dto.getPostId());
        if (!member.getEmail().equals(comment.getMember().getEmail()) && member.getRoles()
            .equals(RoleEnum.USER)) {
            throw new AuthorizationException("권한이 없습니다.");
        }
        comment.setContent(dto.getContent());
    }
}
