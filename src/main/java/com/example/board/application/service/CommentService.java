package com.example.board.application.service;

import com.example.board.adapter.ports.out.persistence.comment.CommentDao;
import com.example.board.adapter.ports.out.persistence.member.MemberDao;
import com.example.board.adapter.ports.out.persistence.post.PostDao;
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
import com.example.board.infrastructure.exception.NotFoundCommentException;
import com.example.board.infrastructure.exception.NotFoundMemberException;
import com.example.board.infrastructure.exception.NotFoundPostException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService implements SaveCommentUseCase, UpdateCommentUseCase,
    DeclarationCommentUseCase, DeleteCommentUseCase {

    private final MemberDao memberDao;
    private final PostDao postDao;
    private final CommentDao commentDao;

    @Autowired
    public CommentService(MemberDao memberDao, PostDao postDao, CommentDao commentDao) {
        this.memberDao = memberDao;
        this.postDao = postDao;
        this.commentDao = commentDao;
    }

    @Transactional
    @Override
    public void save(String email, Long postId, CommentCreateServiceDto dto) {
        Post post = findPostById(postId);
        Member member = findMemberByEmail(email);
        Comment comment = Comment.create(dto.getContent(), post, member);
        commentDao.save(comment);
    }

    @Transactional
    @Override

    public void delete(Long postId, Long commentId, String email) {
        Member member = findMemberByEmail(email);
        Comment comment = findCommentByIdAndPostId(commentId, postId);
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
        Member member = findMemberByEmail(email);
        Comment comment = findCommentByIdAndPostId(commentId, postId);
        if (!member.getEmail().equals(comment.getMember().getEmail()) && member.getRole()
            .equals(MemberRoleEnum.USER)) {
            throw new AuthorizationException("권한이 없습니다.");
        }
        comment.setContent(dto.getContent());
    }

    @Transactional
    @Override
    public void declaration(Long postId, Long commentId) {
        Comment comment = findCommentByIdAndPostId(commentId, postId);
        comment.delete();     // Soft Delete 방식 적용
        if (comment.getMember().getRole().equals(MemberRoleEnum.USER)) {
            comment.getMember().updateBlockStatus(true);
        }
    }

    private Comment findCommentByIdAndPostId(Long commentId, Long postId) {
        return commentDao.findByIdAndPostId(commentId, postId).orElseThrow(
            () -> new NotFoundCommentException("존재하지 않는 댓글입니다.")
        );
    }

    private Member findMemberByEmail(String email) {
        return memberDao.findByEmailAndIsDeletedFalse(email).orElseThrow(
            () -> new NotFoundMemberException("존재하지 않는 사용자입니다.")
        );
    }

    private Post findPostById(Long postId) {
        return postDao.findById(postId).orElseThrow(
            () -> new NotFoundPostException("존재하지 않는 게시글입니다.")
        );
    }
}
