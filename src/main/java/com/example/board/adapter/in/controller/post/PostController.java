package com.example.board.adapter.in.controller.post;

import com.example.board.adapter.ports.in.dto.request.post.PostCreateRequest;
import com.example.board.adapter.ports.in.dto.request.post.PostUpdateRequest;
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
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
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
    public String postList(@RequestParam(required = false, name = "categoryId") Long categoryId,
        @PageableDefault Pageable pageable, Model model) {
        Page<PostListResponse> posts = getPostListUseCase.getPostList(categoryId, pageable);

        model.addAttribute("posts", posts);
        model.addAttribute("categoryId", categoryId);

        return "posts/list";
    }

    // 게시글 검색
    @GetMapping("/search")
    public String searchPost(@RequestParam(required = false, name = "keyword") String keyword,
        @RequestParam(required = false, name = "categoryId") Long categoryId,
        @PageableDefault Pageable pageable, Model model) {
        Page<PostListResponse> posts = searchPostUseCase.search(keyword, categoryId, pageable);

        model.addAttribute("posts", posts);
        model.addAttribute("categoryId", categoryId);

        return "posts/list";
    }

    // 게시글 등록 페이지 이동
    @GetMapping("/registerForm")
    public String registerFreeBoardForm() {
        return "posts/registerForm";
    }

    // 게시글 등록
    @PostMapping("/register")
    public String registerFreeBoard(PostCreateRequest postCreateRequest, Principal principal) {
        PostCreateServiceDto serviceDto = new PostCreateServiceDto(postCreateRequest);
        savePostUseCase.save(serviceDto, principal.getName());

        return "redirect:/posts";
    }

    // 게시글 상세보기
    @GetMapping("/{postId}")
    public String detailPost(@PathVariable(name = "postId") Long postId, Model model) {
        PostDetailResponse post = getPostDetailUseCase.getPost(postId);

        model.addAttribute("post", post);

        return "posts/detail";
    }

    // 게시글 수정 페이지 이동
    @GetMapping("/{postId}/updateForm")
    public String updateForm(@PathVariable(name = "postId") Long postId, Model model) {
        PostDetailResponse post = getPostDetailUseCase.getPost(postId);

        model.addAttribute("post", post);

        return "posts/updateForm";
    }

    // 게시글 수정
    @PostMapping("/{postId}/update")
    public String updatePost(@PathVariable(name = "postId") Long postId,
        PostUpdateRequest postUpdateRequest, Principal principal) {
        PostUpdateServiceDto serviceDto = new PostUpdateServiceDto(postUpdateRequest);
        updatePostUseCase.update(postId, serviceDto, principal.getName());

        return "redirect:/posts";
    }

    // 게시글 삭제
    @PostMapping("/{postId}/delete")
    public String deletePost(@PathVariable(name = "postId") Long postId, Principal principal) {
        deletePostUseCase.delete(postId, principal.getName());

        return "redirect:/posts";
    }

    // 게시글 신고하기
    @PostMapping("/{postId}/declaration")
    public String inquiryPost(@PathVariable(name = "postId") Long postId) {
        declarationPostUseCase.declaration(postId);

        return "redirect:/posts";
    }

    // 게시글 좋아요
    @PostMapping("{postId}/like")
    public String likePost(@PathVariable(name = "postId") Long postId) {
        likePostUseCase.like(postId);

        return "redirect:/posts/" + postId;
    }
}
