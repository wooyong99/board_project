package com.example.board.application.usecase.comment;

import com.example.board.application.usecase.comment.dto.DeleteCommentServiceDto;
import com.example.board.domain.entity.Comment;
import com.example.board.domain.entity.Member;
import com.example.board.domain.entity.MemberRoleEnum;
import com.example.board.domain.repository.CommentRepository;
import com.example.board.domain.repository.MemberRepository;
import com.example.board.exception.AuthorizationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteCommentUseCaseImpl implements DeleteCommentUseCase {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    public DeleteCommentUseCaseImpl(MemberRepository memberRepository,
        CommentRepository commentRepository) {
        this.memberRepository = memberRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public void delete(DeleteCommentServiceDto dto) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(dto.getEmail());
        Comment comment = commentRepository.findByIdAndPostId(dto.getCommentId(), dto.getPostId());
        if (!member.getEmail().equals(comment.getMember().getEmail()) && member.getRole()
            .equals(MemberRoleEnum.USER)) {
            throw new AuthorizationException("권한이 없습니다.");
        }
        comment.delete();     // Soft Delete 방식 적용
    }
}
