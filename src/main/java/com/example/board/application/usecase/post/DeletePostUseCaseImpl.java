package com.example.board.application.usecase.post;

import com.example.board.application.usecase.post.dto.DeletePostServiceDto;
import com.example.board.domain.entity.Member;
import com.example.board.domain.entity.Post;
import com.example.board.domain.entity.RoleEnum;
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
    public void delete(DeletePostServiceDto dto) {
        Member member = memberRepository.findByIdAndIsDeletedFalse(dto.getMemberId());
        Post post = postRepository.findById(dto.getPostId());
        if (!post.getMember().getEmail().equals(member.getEmail()) && member.getRoles()
            .equals(RoleEnum.USER)) {
            throw new AuthorizationException("권한이 없습니다.");
        }
        post.delete();    // Soft Delete 방식 적용
    }
}
