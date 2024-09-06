package com.example.board.application.usecase.comment;

import com.example.board.application.usecase.comment.dto.SaveCommentServiceDto;
import com.example.board.domain.entity.Comment;
import com.example.board.domain.entity.Member;
import com.example.board.domain.entity.Post;
import com.example.board.domain.repository.CommentRepository;
import com.example.board.domain.repository.MemberRepository;
import com.example.board.domain.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaveCommentUseCaseImpl implements SaveCommentUseCase {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    public SaveCommentUseCaseImpl(PostRepository postRepository,
        MemberRepository memberRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public void save(SaveCommentServiceDto dto) {
        Post post = postRepository.findById(dto.getPostId());
        Member member = memberRepository.findByEmailAndIsDeletedFalse(dto.getEmail());
        Comment comment = Comment.create(dto.getContent(), post, member);
        commentRepository.save(comment);
    }
}
