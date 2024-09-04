package com.example.board.domain.post.service;

import com.example.board.domain.category.dao.CategoryDao;
import com.example.board.domain.category.entity.Category;
import com.example.board.domain.member.dao.MemberDao;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.entity.MemberRoleEnum;
import com.example.board.domain.post.dao.PostDao;
import com.example.board.domain.post.dto.PostCreateServiceDto;
import com.example.board.domain.post.dto.PostDetailResponse;
import com.example.board.domain.post.dto.PostListResponse;
import com.example.board.domain.post.dto.PostUpdateServiceDto;
import com.example.board.domain.post.entity.Post;
import com.example.board.global.exception.AuthorizationException;
import com.example.board.global.exception.NotFoundCategoryException;
import com.example.board.global.exception.NotFoundMemberException;
import com.example.board.global.exception.NotFoundPostException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final MemberDao memberDao;

    private final PostDao postDao;

    private final CategoryDao categoryDao;

    public PostService(MemberDao memberdao, PostDao postDao, CategoryDao categoryDao) {
        this.memberDao = memberdao;
        this.postDao = postDao;
        this.categoryDao = categoryDao;
    }

    // 게시글 리스트 조회
    @Transactional(readOnly = true)
    public Page<PostListResponse> getPostList(Long categoryId, Pageable pageable) {
        return postDao.findPostList(categoryId, null, pageable);
    }

    @Transactional(readOnly = true)
    public Page<PostListResponse> search(String keyword, Long categoryId, Pageable pageable) {
        return postDao.findPostList(categoryId, keyword, pageable);
    }

    // 게시글 단건 조회
    @Transactional(readOnly = true)
    public PostDetailResponse getPost(Long postId) {
        return postDao.findPost(postId);
    }

    // 게시글 저장
    @Transactional
    public void save(PostCreateServiceDto dto, String email) {
        Member member = findMemberByEmail(email);
        Category category = findCategoryById(dto.getCategoryId());

        Post post = new Post(dto.getTitle(), dto.getContent(), member, category);

        postDao.save(post);
    }

    // 게시글 수정
    @Transactional
    public void update(Long postId, PostUpdateServiceDto dto, String email) {
        Member member = findMemberByEmail(email);
        Post post = findPostById(postId);
        if (!post.getMember().getEmail().equals(member.getEmail()) && member.getRole()
            .equals(MemberRoleEnum.USER)) {
            throw new AuthorizationException("권한이 없습니다.");
        }
        post.update(dto);
    }

    // 게시글 삭제
    @Transactional
    public void delete(Long postId, String email) {
        Member member = findMemberByEmail(email);
        Post post = findPostById(postId);
        if (!post.getMember().getEmail().equals(member.getEmail()) && member.getRole()
            .equals(MemberRoleEnum.USER)) {
            throw new AuthorizationException("권한이 없습니다.");
        }
        post.setIsDeleted(true);    // Soft Delete 방식 적용
    }

    // 게시글 신고하기
    @Transactional
    public void declaration(Long postId) {
        Post post = findPostById(postId);
        post.setIsDeleted(true);    // Soft Delete 방식 적용
        if (post.getMember().getRole().equals(MemberRoleEnum.USER)) {
            post.getMember().updateBlockStatus(true);
        }
    }

    // 게시글 좋아요
    @Transactional
    public void like(Long postId) {
        Post post = findPostById(postId);
        post.increaseLike();
    }

    private Post findPostById(Long postId) {
        return postDao.findById(postId).orElseThrow(
            () -> new NotFoundPostException("존재하지 않는 게시글입니다.")
        );
    }

    private Member findMemberByEmail(String email) {
        return memberDao.findByEmailAndIsDeletedFalse(email).orElseThrow(
            () -> new NotFoundMemberException("존재하지 않는 사용자입니다.")
        );
    }

    private Category findCategoryById(Long categoryId) {
        return categoryDao.findById(categoryId).orElseThrow(
            () -> new NotFoundCategoryException("존재하지 않는 카테고리입니다.")
        );
    }
}
