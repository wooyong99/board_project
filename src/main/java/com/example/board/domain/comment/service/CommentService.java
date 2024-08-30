package com.example.board.domain.comment.service;

import com.example.board.domain.comment.dao.CommentDao;
import com.example.board.domain.comment.dto.CommentCreateRequest;
import com.example.board.domain.comment.dto.CommentUpdateRequest;
import com.example.board.domain.comment.entity.Comment;
import com.example.board.domain.member.dao.MemberDao;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.entity.MemberRoleEnum;
import com.example.board.domain.post.dao.PostDao;
import com.example.board.domain.post.entity.Post;
import com.example.board.global.exception.AuthorizationException;
import com.example.board.global.exception.NotFoundCommentException;
import com.example.board.global.exception.NotFoundMemberException;
import com.example.board.global.exception.NotFoundPostException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final MemberDao memberDao;
    private final PostDao postDao;
    private final CommentDao commentDao;

    @Transactional
    public void save(String email, Long postId, CommentCreateRequest request) {
        Post post = findPostById(postId);
        Member member = findMemberByEmail(email);
        Comment comment = Comment.builder()
            .content(request.getContent())
            .post(post)
            .member(member)
            .build();
        commentDao.save(comment);
    }

    @Transactional
    public void delete(Long postId, Long commentId, String email) {
        Member member = findMemberByEmail(email);
        Comment comment = findCommentByIdAndPostId(commentId, postId);
        if (!member.getEmail().equals(comment.getMember().getEmail()) && member.getRole()
            .equals(MemberRoleEnum.USER)) {
            throw new AuthorizationException("권한이 없습니다.");
        }
        commentDao.deleteById(comment.getId());
    }

    @Transactional
    public void update(Long postId, Long commentId, CommentUpdateRequest request,
        String email) {
        Member member = findMemberByEmail(email);
        Comment comment = findCommentByIdAndPostId(commentId, postId);
        if (!member.getEmail().equals(comment.getMember().getEmail()) && member.getRole()
            .equals(MemberRoleEnum.USER)) {
            throw new AuthorizationException("권한이 없습니다.");
        }
        comment.setContent(request.getContent());
    }

    @Transactional
    public void declaration(Long postId, Long commentId) {
        Comment comment = findCommentByIdAndPostId(commentId, postId);
        commentDao.deleteById(comment.getId());
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
        return memberDao.findByEmail(email).orElseThrow(
            () -> new NotFoundMemberException("존재하지 않는 사용자입니다.")
        );
    }

    private Post findPostById(Long postId) {
        return postDao.findById(postId).orElseThrow(
            () -> new NotFoundPostException("존재하지 않는 게시글입니다.")
        );
    }
}
