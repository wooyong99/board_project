package com.example.board.application.usecase.post;

import com.example.board.application.usecase.post.dto.SavePostServiceDto;
import com.example.board.domain.entity.Category;
import com.example.board.domain.entity.Member;
import com.example.board.domain.entity.Post;
import com.example.board.domain.repository.CategoryRepository;
import com.example.board.domain.repository.MemberRepository;
import com.example.board.domain.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SavePostUseCaseImpl implements SavePostUseCase {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    public SavePostUseCaseImpl(MemberRepository memberRepository,
        CategoryRepository categoryRepository,
        PostRepository postRepository) {
        this.memberRepository = memberRepository;
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
    }

    @Override
    @Transactional
    public void save(SavePostServiceDto dto) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(dto.getEmail());
        Category category = categoryRepository.findById(dto.getCategoryId());

        Post post = new Post(dto.getTitle(), dto.getContent(), member, category);

        postRepository.save(post);
    }
}
