package com.example.board.domain.post.controller;

import com.example.board.domain.post.dto.PostCreateRequest;
import com.example.board.domain.post.dto.PostDetailResponse;
import com.example.board.domain.post.dto.PostListResponse;
import com.example.board.domain.post.dto.PostUpdateRequest;
import com.example.board.domain.post.service.PostService;
import java.security.Principal;
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

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 리스트 조회
    @GetMapping
    public String postList(@RequestParam(required = false, name = "categoryId") Long categoryId,
        @PageableDefault Pageable pageable, Model model) {
        Page<PostListResponse> posts = postService.getPostList(categoryId, pageable);

        model.addAttribute("posts", posts);
        model.addAttribute("categoryId", categoryId);

        return "posts/list";
    }

    // 게시글 검색
    @GetMapping("/search")
    public String searchPost(@RequestParam(required = false, name = "keyword") String keyword,
        @RequestParam(required = false, name = "categoryId") Long categoryId,
        @PageableDefault Pageable pageable, Model model) {
        Page<PostListResponse> posts = postService.search(keyword, categoryId, pageable);

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
        postService.save(postCreateRequest, principal.getName());

        return "redirect:/posts";
    }

    // 게시글 상세보기
    @GetMapping("/{postId}")
    public String detailPost(@PathVariable(name = "postId") Long postId, Model model) {
        PostDetailResponse post = postService.getPost(postId);

        model.addAttribute("post", post);

        return "posts/detail";
    }

    // 게시글 수정 페이지 이동
    @GetMapping("/{postId}/updateForm")
    public String updateForm(@PathVariable(name = "postId") Long postId, Model model) {
        PostDetailResponse post = postService.getPost(postId);

        model.addAttribute("post", post);

        return "posts/updateForm";
    }

    // 게시글 수정
    @PostMapping("/{postId}/update")
    public String updatePost(@PathVariable(name = "postId") Long postId,
        PostUpdateRequest postUpdateRequest, Principal principal) {
        postService.update(postId, postUpdateRequest, principal.getName());

        return "redirect:/posts";
    }

    // 게시글 삭제
    @PostMapping("/{postId}/delete")
    public String deletePost(@PathVariable(name = "postId") Long postId, Principal principal) {
        postService.delete(postId, principal.getName());

        return "redirect:/posts";
    }

    // 게시글 신고하기
    @PostMapping("/{postId}/declaration")
    public String inquiryPost(@PathVariable(name = "postId") Long postId) {
        postService.declaration(postId);

        return "redirect:/posts";
    }

    // 게시글 좋아요
    @PostMapping("{postId}/like")
    public String likePost(@PathVariable(name = "postId") Long postId) {
        postService.like(postId);

        return "redirect:/posts/" + postId;
    }
}
