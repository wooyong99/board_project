package com.example.board.api.comment;

import com.example.board.persistence.comment.dto.CommentCreateRequest;
import com.example.board.persistence.comment.dto.CommentCreateServiceDto;
import com.example.board.persistence.comment.dto.CommentUpdateRequest;
import com.example.board.persistence.comment.dto.CommentUpdateServiceDto;
import com.example.board.persistence.comment.service.CommentService;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 저장
    @PostMapping("/posts/{postId}/comments")
    public String registerComment(@PathVariable(name = "postId") Long postId,
        CommentCreateRequest commentCreateRequest, Principal principal) {
        CommentCreateServiceDto serviceDto = new CommentCreateServiceDto(commentCreateRequest);
        commentService.save(principal.getName(), postId, serviceDto);

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
        CommentUpdateServiceDto serviceDto = new CommentUpdateServiceDto(commentUpdateRequest);
        commentService.update(postId, commentId, serviceDto,
            principal.getName());

        return "redirect:/posts/" + postId;
    }

    // 댓글 신고하기
    @PostMapping("/posts/{postId}/comments/{commentId}/declaration")
    public String inquiryComment(@PathVariable(name = "postId") Long postId,
        @PathVariable(name = "commentId") Long commentId) {
        commentService.declaration(postId, commentId);

        return "redirect:/posts/" + postId;
    }

}
