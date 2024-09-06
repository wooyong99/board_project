package com.example.board.application.usecase.post;

import com.example.board.application.usecase.post.dto.UpdatePostServiceDto;
import com.example.board.domain.entity.Member;
import com.example.board.domain.entity.MemberRoleEnum;
import com.example.board.domain.entity.Post;
import com.example.board.domain.repository.MemberRepository;
import com.example.board.domain.repository.PostRepository;
import com.example.board.exception.AuthorizationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdatePostUseCaseImpl implements UpdatePostUseCase {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public UpdatePostUseCaseImpl(MemberRepository memberRepository, PostRepository postRepository) {
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
    }

    @Override
    @Transactional
    public void update(UpdatePostServiceDto dto) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(dto.getEmail());
        Post post = postRepository.findById(dto.getPostId());
        if (!post.getMember().getEmail().equals(member.getEmail()) && member.getRole()
            .equals(MemberRoleEnum.USER)) {
            throw new AuthorizationException("권한이 없습니다.");
        }
        post.update(dto);
    }
}
