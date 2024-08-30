package com.example.board.domain.post.controller;

import com.example.board.domain.post.dto.PostCreateRequest;
import com.example.board.domain.post.dto.PostListResponse;
import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.service.PostService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 리스트 조회
    @GetMapping
    public String postList(@RequestParam(required = false, name = "categoryId") Long categoryId,
        @PageableDefault Pageable pageable, Model model) {
        Page<PostListResponse> posts = postService.getPostList(categoryId, pageable);

        model.addAttribute("posts", posts);
        model.addAttribute("category", categoryId);

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
        Post post = postService.getPost(postId);

        model.addAttribute("post", post);

        return "posts/detail";
    }
}
