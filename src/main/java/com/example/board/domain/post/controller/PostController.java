package com.example.board.domain.post.controller;

import com.example.board.domain.post.dto.PostCreateRequest;
import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.service.PostService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

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
