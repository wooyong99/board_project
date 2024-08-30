package com.example.board.domain.post.service;

import com.example.board.domain.category.dao.CategoryDao;
import com.example.board.domain.category.entity.Category;
import com.example.board.domain.member.dao.MemberDao;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.post.dao.PostDao;
import com.example.board.domain.post.dto.PostCreateRequest;
import com.example.board.domain.post.entity.Post;
import com.example.board.global.exception.NotFoundCategoryException;
import com.example.board.global.exception.NotFoundMemberException;
import com.example.board.global.exception.NotFoundPostException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final MemberDao memberDao;

    private final PostDao postDao;

    private final CategoryDao categoryDao;

    // 게시글 단건 조회
    @Transactional(readOnly = true)
    public Post getPost(Long postId) {
        Post post = findPostById(postId);
        return post;
    }

    // 게시글 저장
    @Transactional
    public void save(PostCreateRequest request, String email) {
        Member member = findMemberByEmail(email);
        Category category = findCategoryById(request.getCategoryId());

        Post post = Post.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .member(member)
            .category(category)
            .build();

        postDao.save(post);
    }

    private Post findPostById(Long postId) {
        return postDao.findById(postId).orElseThrow(
            () -> new NotFoundPostException("존재하지 않는 게시글입니다.")
        );
    }

    private Member findMemberByEmail(String email) {
        return memberDao.findByEmail(email).orElseThrow(
            () -> new NotFoundMemberException("존재하지 않는 사용자입니다.")
        );
    }

    private Category findCategoryById(Long categoryId) {
        return categoryDao.findById(categoryId).orElseThrow(
            () -> new NotFoundCategoryException("존재하지 않는 카테고리입니다.")
        );
    }
}
