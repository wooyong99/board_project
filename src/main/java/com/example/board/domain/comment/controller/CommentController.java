package com.example.board.domain.comment.controller;

import com.example.board.domain.comment.dto.CommentCreateRequest;
import com.example.board.domain.comment.dto.CommentUpdateRequest;
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

    // 댓글 삭제
    @PostMapping("/posts/{postId}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable(name = "postId") Long postId,
        @PathVariable(name = "commentId") Long commentId, Principal principal) {
        commentService.delete(postId, commentId, principal.getName());

        return "redirect:/posts/" + postId;
    }

    // 댓글 수정
    @PostMapping("/posts/{postId}/comments/{commentId}/update")
    public String updateComment(@PathVariable(name = "postId") Long postId,
        @PathVariable(name = "commentId") Long commentId,
        CommentUpdateRequest commentUpdateRequest, Principal principal) {
        commentService.update(postId, commentId, commentUpdateRequest, principal.getName());

        return "redirect:/posts/" + postId;
    }

}
