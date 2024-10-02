package com.example.board.api.controller.post;

import com.example.board.api.controller.post.request.PostCreateRequest;
import com.example.board.api.controller.post.request.PostUpdateRequest;
import com.example.board.api.controller.post.response.PostDetailResponse;
import com.example.board.api.controller.post.response.PostListResponse;
import com.example.board.application.usecase.post.DeclarationPostUseCase;
import com.example.board.application.usecase.post.DeletePostUseCase;
import com.example.board.application.usecase.post.GetPostDetailUseCase;
import com.example.board.application.usecase.post.GetPostListUseCase;
import com.example.board.application.usecase.post.LikePostUseCase;
import com.example.board.application.usecase.post.SavePostUseCase;
import com.example.board.application.usecase.post.SearchPostUseCase;
import com.example.board.application.usecase.post.UpdatePostUseCase;
import com.example.board.application.usecase.post.dto.DeclarationPostServiceDto;
import com.example.board.application.usecase.post.dto.DeletePostServiceDto;
import com.example.board.application.usecase.post.dto.GetPostDetailServiceDto;
import com.example.board.application.usecase.post.dto.GetPostListServiceDto;
import com.example.board.application.usecase.post.dto.LikePostServiceDto;
import com.example.board.application.usecase.post.dto.SavePostServiceDto;
import com.example.board.application.usecase.post.dto.SearchPostServiceDto;
import com.example.board.application.usecase.post.dto.UpdatePostServiceDto;
import com.example.board.domain.entity.RoleEnum;
import com.example.board.infrastructure.aop.AuthorizationRequired;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final GetPostListUseCase getPostListUseCase;
    private final GetPostDetailUseCase getPostDetailUseCase;
    private final UpdatePostUseCase updatePostUseCase;
    private final SavePostUseCase savePostUseCase;
    private final SearchPostUseCase searchPostUseCase;
    private final DeletePostUseCase deletePostUseCase;
    private final DeclarationPostUseCase declarationPostUseCase;
    private final LikePostUseCase likePostUseCase;

    @Autowired
    public PostController(GetPostListUseCase getPostListUseCase,
        GetPostDetailUseCase getPostDetailUseCase, UpdatePostUseCase updatePostUseCase,
        SavePostUseCase savePostUseCase,
        SearchPostUseCase searchPostUseCase, DeletePostUseCase deletePostUseCase,
        DeclarationPostUseCase declarationPostUseCase, LikePostUseCase likePostUseCase) {
        this.getPostListUseCase = getPostListUseCase;
        this.getPostDetailUseCase = getPostDetailUseCase;
        this.updatePostUseCase = updatePostUseCase;
        this.savePostUseCase = savePostUseCase;
        this.searchPostUseCase = searchPostUseCase;
        this.deletePostUseCase = deletePostUseCase;
        this.declarationPostUseCase = declarationPostUseCase;
        this.likePostUseCase = likePostUseCase;
    }

    // 게시글 리스트 조회
    @GetMapping
    public ResponseEntity postList(
        @RequestParam(required = false, name = "categoryId") Long categoryId,
        @PageableDefault Pageable pageable) {
        GetPostListServiceDto serviceDto = new GetPostListServiceDto(categoryId, pageable);
        Page<PostListResponse> posts = getPostListUseCase.getPostList(serviceDto);

        return ResponseEntity.ok(posts);
    }

    // 게시글 검색
    @GetMapping("/search")
    public ResponseEntity searchPost(
        @RequestParam(required = false, name = "keyword") String keyword,
        @RequestParam(required = false, name = "categoryId") Long categoryId,
        @PageableDefault Pageable pageable, Model model) {
        SearchPostServiceDto serviceDto = new SearchPostServiceDto(keyword, categoryId, pageable);
        Page<PostListResponse> posts = searchPostUseCase.search(serviceDto);

        return ResponseEntity.ok(posts);
    }

    // 게시글 등록
    @PostMapping
    @AuthorizationRequired({RoleEnum.EXERCISE_ADMIN, RoleEnum.USER})
    public ResponseEntity<Object> createPost(@RequestBody PostCreateRequest postCreateRequest,
        Principal principal) {
        SavePostServiceDto serviceDto = new SavePostServiceDto(postCreateRequest.getTitle(),
            postCreateRequest.getContent(), postCreateRequest.getCategoryId(),
            Long.parseLong(principal.getName()));
        savePostUseCase.save(serviceDto);

        return ResponseEntity.ok().build();
    }

    // 게시글 상세보기
    @GetMapping("/{postId}")
    public ResponseEntity detailPost(@PathVariable(name = "postId") Long postId) {
        GetPostDetailServiceDto serviceDto = new GetPostDetailServiceDto(postId);
        PostDetailResponse postDetail = getPostDetailUseCase.getPost(serviceDto);

        return ResponseEntity.ok(postDetail);
    }

    // 게시글 수정
    @PutMapping("/{postId}")
    @AuthorizationRequired({RoleEnum.EXERCISE_ADMIN, RoleEnum.USER})
    public ResponseEntity updatePost(@PathVariable(name = "postId") Long postId,
        @RequestBody PostUpdateRequest postUpdateRequest, Principal principal) {
        UpdatePostServiceDto serviceDto = new UpdatePostServiceDto(postUpdateRequest.getTitle(),
            postUpdateRequest.getContent(), postId, Long.parseLong(principal.getName()));
        updatePostUseCase.update(serviceDto);

        return ResponseEntity.ok().build();
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    @AuthorizationRequired({RoleEnum.EXERCISE_ADMIN, RoleEnum.USER})
    public ResponseEntity deletePost(@PathVariable(name = "postId") Long postId,
        Principal principal) {
        DeletePostServiceDto serviceDto = new DeletePostServiceDto(postId,
            Long.parseLong(principal.getName()));
        deletePostUseCase.delete(serviceDto);

        return ResponseEntity.noContent().build();
    }

    // 게시글 신고하기
    @PutMapping("/{postId}/declaration")
    public ResponseEntity inquiryPost(@PathVariable(name = "postId") Long postId) {
        DeclarationPostServiceDto serviceDto = new DeclarationPostServiceDto(postId);
        declarationPostUseCase.declaration(serviceDto);

        return ResponseEntity.noContent().build();
    }

    // 게시글 좋아요
    @PostMapping("/{postId}/like")
    public ResponseEntity likePost(@PathVariable(name = "postId") Long postId) {
        LikePostServiceDto serviceDto = new LikePostServiceDto(postId);
        Long response = likePostUseCase.like(serviceDto);

        return ResponseEntity.ok(response);
    }
}
