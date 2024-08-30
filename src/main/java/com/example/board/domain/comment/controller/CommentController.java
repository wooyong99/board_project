package com.example.board.domain.comment.controller;

import com.example.board.domain.comment.dto.CommentCreateRequest;
import com.example.board.domain.comment.service.CommentService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 저장
    @PostMapping("/posts/{postId}/comments")
    public String registerComment(@PathVariable(name = "postId") Long postId,
        CommentCreateRequest commentCreateRequest, Principal principal) {
        commentService.save(principal.getName(), postId, commentCreateRequest);

        return "redirect:/posts/" + postId;
    }
}
