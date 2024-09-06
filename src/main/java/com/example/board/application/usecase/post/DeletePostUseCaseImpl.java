package com.example.board.application.usecase.post;

import com.example.board.domain.entity.Member;
import com.example.board.domain.entity.MemberRoleEnum;
import com.example.board.domain.entity.Post;
import com.example.board.domain.repository.MemberRepository;
import com.example.board.domain.repository.PostRepository;
import com.example.board.exception.AuthorizationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeletePostUseCaseImpl implements DeletePostUseCase {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public DeletePostUseCaseImpl(MemberRepository memberRepository, PostRepository postRepository) {
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
    }

    @Override
    @Transactional
    public void delete(Long postId, String email) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(email);
        Post post = postRepository.findById(postId);
        if (!post.getMember().getEmail().equals(member.getEmail()) && member.getRole()
            .equals(MemberRoleEnum.USER)) {
            throw new AuthorizationException("권한이 없습니다.");
        }
        post.delete();    // Soft Delete 방식 적용
    }
}
