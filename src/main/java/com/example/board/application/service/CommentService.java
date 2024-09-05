package com.example.board.application.service;

import com.example.board.adapter.out.persistence.comment.CommentRepositoryAdapter;
import com.example.board.adapter.out.persistence.member.MemberRepositoryAdapter;
import com.example.board.adapter.out.persistence.post.PostRepositoryAdapter;
import com.example.board.application.port.in.comment.DeclarationCommentUseCase;
import com.example.board.application.port.in.comment.DeleteCommentUseCase;
import com.example.board.application.port.in.comment.SaveCommentUseCase;
import com.example.board.application.port.in.comment.UpdateCommentUseCase;
import com.example.board.application.service.dto.CommentCreateServiceDto;
import com.example.board.application.service.dto.CommentUpdateServiceDto;
import com.example.board.domain.entity.Comment;
import com.example.board.domain.entity.Member;
import com.example.board.domain.entity.MemberRoleEnum;
import com.example.board.domain.entity.Post;
import com.example.board.infrastructure.exception.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService implements SaveCommentUseCase, UpdateCommentUseCase,
    DeclarationCommentUseCase, DeleteCommentUseCase {

    private final MemberRepositoryAdapter memberRepositoryAdapter;
    private final PostRepositoryAdapter postRepositoryAdapter;
    private final CommentRepositoryAdapter commentRepositoryAdapter;

    @Autowired
    public CommentService(MemberRepositoryAdapter memberRepositoryAdapter,
        PostRepositoryAdapter postRepositoryAdapter,
        CommentRepositoryAdapter commentRepositoryAdapter) {
        this.memberRepositoryAdapter = memberRepositoryAdapter;
        this.postRepositoryAdapter = postRepositoryAdapter;
        this.commentRepositoryAdapter = commentRepositoryAdapter;
    }

    @Transactional
    @Override
    public void save(String email, Long postId, CommentCreateServiceDto dto) {
        Post post = postRepositoryAdapter.findById(postId);
        Member member = memberRepositoryAdapter.findByEmailAndIsDeletedFalse(email);
        Comment comment = Comment.create(dto.getContent(), post, member);
        commentRepositoryAdapter.save(comment);
    }

    @Transactional
    @Override

    public void delete(Long postId, Long commentId, String email) {
        Member member = memberRepositoryAdapter.findByEmailAndIsDeletedFalse(email);
        Comment comment = commentRepositoryAdapter.findByIdAndPostId(commentId, postId);
        if (!member.getEmail().equals(comment.getMember().getEmail()) && member.getRole()
            .equals(MemberRoleEnum.USER)) {
            throw new AuthorizationException("권한이 없습니다.");
        }
        comment.delete();     // Soft Delete 방식 적용
    }

    @Transactional
    @Override
    public void update(Long postId, Long commentId, CommentUpdateServiceDto dto,
        String email) {
        Member member = memberRepositoryAdapter.findByEmailAndIsDeletedFalse(email);
        Comment comment = commentRepositoryAdapter.findByIdAndPostId(commentId, postId);
        if (!member.getEmail().equals(comment.getMember().getEmail()) && member.getRole()
            .equals(MemberRoleEnum.USER)) {
            throw new AuthorizationException("권한이 없습니다.");
        }
        comment.setContent(dto.getContent());
    }

    @Transactional
    @Override
    public void declaration(Long postId, Long commentId) {
        Comment comment = commentRepositoryAdapter.findByIdAndPostId(commentId, postId);
        comment.delete();     // Soft Delete 방식 적용
        if (comment.getMember().getRole().equals(MemberRoleEnum.USER)) {
            comment.getMember().updateBlockedAt();
            comment.getMember().updateBlockStatus(true);
        }
    }
}
