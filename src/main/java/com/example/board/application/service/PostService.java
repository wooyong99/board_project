package com.example.board.application.service;

import com.example.board.adapter.out.persistence.category.CategoryRepositoryAdapter;
import com.example.board.adapter.out.persistence.member.MemberRepositoryAdapter;
import com.example.board.adapter.out.persistence.post.PostRepositoryAdapter;
import com.example.board.adapter.ports.in.dto.response.post.PostDetailResponse;
import com.example.board.adapter.ports.in.dto.response.post.PostListResponse;
import com.example.board.application.port.in.post.DeclarationPostUseCase;
import com.example.board.application.port.in.post.DeletePostUseCase;
import com.example.board.application.port.in.post.GetPostDetailUseCase;
import com.example.board.application.port.in.post.GetPostListUseCase;
import com.example.board.application.port.in.post.LikePostUseCase;
import com.example.board.application.port.in.post.SavePostUseCase;
import com.example.board.application.port.in.post.SearchPostUseCase;
import com.example.board.application.port.in.post.UpdatePostUseCase;
import com.example.board.application.service.dto.PostCreateServiceDto;
import com.example.board.application.service.dto.PostUpdateServiceDto;
import com.example.board.domain.entity.Category;
import com.example.board.domain.entity.Member;
import com.example.board.domain.entity.MemberRoleEnum;
import com.example.board.domain.entity.Post;
import com.example.board.infrastructure.exception.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService implements GetPostListUseCase, SearchPostUseCase, GetPostDetailUseCase,
    SavePostUseCase, UpdatePostUseCase, DeletePostUseCase, DeclarationPostUseCase, LikePostUseCase {

    private final MemberRepositoryAdapter memberRepositoryAdapter;

    private final PostRepositoryAdapter postRepositoryAdapter;

    private final CategoryRepositoryAdapter categoryRepositoryAdapter;

    @Autowired
    public PostService(MemberRepositoryAdapter memberRepositoryAdapter,
        PostRepositoryAdapter postRepositoryAdapter,
        CategoryRepositoryAdapter categoryRepositoryAdapter) {
        this.memberRepositoryAdapter = memberRepositoryAdapter;
        this.postRepositoryAdapter = postRepositoryAdapter;
        this.categoryRepositoryAdapter = categoryRepositoryAdapter;
    }

    // 게시글 리스트 조회
    @Transactional(readOnly = true)
    @Override
    public Page<PostListResponse> getPostList(Long categoryId, Pageable pageable) {
        return postRepositoryAdapter.findPostList(categoryId, null, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PostListResponse> search(String keyword, Long categoryId, Pageable pageable) {
        return postRepositoryAdapter.findPostList(categoryId, keyword, pageable);
    }

    // 게시글 단건 조회
    @Transactional(readOnly = true)
    @Override
    public PostDetailResponse getPost(Long postId) {
        return postRepositoryAdapter.findPost(postId);
    }

    // 게시글 저장
    @Transactional
    @Override
    public void save(PostCreateServiceDto dto, String email) {
        Member member = memberRepositoryAdapter.findByEmailAndIsDeletedFalse(email);
        Category category = categoryRepositoryAdapter.findById(dto.getCategoryId());

        Post post = new Post(dto.getTitle(), dto.getContent(), member, category);

        postRepositoryAdapter.save(post);
    }

    // 게시글 수정
    @Transactional
    @Override
    public void update(Long postId, PostUpdateServiceDto dto, String email) {
        Member member = memberRepositoryAdapter.findByEmailAndIsDeletedFalse(email);
        Post post = postRepositoryAdapter.findById(postId);
        if (!post.getMember().getEmail().equals(member.getEmail()) && member.getRole()
            .equals(MemberRoleEnum.USER)) {
            throw new AuthorizationException("권한이 없습니다.");
        }
        post.update(dto);
    }

    // 게시글 삭제
    @Transactional
    @Override
    public void delete(Long postId, String email) {
        Member member = memberRepositoryAdapter.findByEmailAndIsDeletedFalse(email);
        Post post = postRepositoryAdapter.findById(postId);
        if (!post.getMember().getEmail().equals(member.getEmail()) && member.getRole()
            .equals(MemberRoleEnum.USER)) {
            throw new AuthorizationException("권한이 없습니다.");
        }
        post.delete();    // Soft Delete 방식 적용
    }

    // 게시글 신고하기
    @Transactional
    @Override
    public void declaration(Long postId) {
        Post post = postRepositoryAdapter.findById(postId);
        post.delete();    // Soft Delete 방식 적용
        if (post.getMember().getRole().equals(MemberRoleEnum.USER)) {
            post.getMember().updateBlockStatus(true);
        }
    }

    // 게시글 좋아요
    @Transactional
    @Override
    public void like(Long postId) {
        Post post = postRepositoryAdapter.findById(postId);
        post.increaseLike();
    }
}
