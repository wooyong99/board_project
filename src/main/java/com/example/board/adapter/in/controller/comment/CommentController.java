package com.example.board.adapter.ports.in.controller.comment;

import com.example.board.adapter.ports.in.dto.request.comment.CommentCreateRequest;
import com.example.board.adapter.ports.in.dto.request.comment.CommentUpdateRequest;
import com.example.board.application.port.in.comment.DeclarationCommentUseCase;
import com.example.board.application.port.in.comment.DeleteCommentUseCase;
import com.example.board.application.port.in.comment.SaveCommentUseCase;
import com.example.board.application.port.in.comment.UpdateCommentUseCase;
import com.example.board.application.service.dto.CommentCreateServiceDto;
import com.example.board.application.service.dto.CommentUpdateServiceDto;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    private final SaveCommentUseCase saveCommentUseCase;
    private final DeleteCommentUseCase deleteCommentUseCase;
    private final DeclarationCommentUseCase declarationCommentUseCase;
    private final UpdateCommentUseCase updateCommentUseCase;

    @Autowired
    public CommentController(SaveCommentUseCase saveCommentUseCase,
        DeleteCommentUseCase deleteCommentUseCase,
        DeclarationCommentUseCase declarationCommentUseCase,
        UpdateCommentUseCase updateCommentUseCase) {
        this.saveCommentUseCase = saveCommentUseCase;
        this.deleteCommentUseCase = deleteCommentUseCase;
        this.declarationCommentUseCase = declarationCommentUseCase;
        this.updateCommentUseCase = updateCommentUseCase;
    }

    // 댓글 저장
    @PostMapping("/posts/{postId}/comments")
    public String registerComment(@PathVariable(name = "postId") Long postId,
        CommentCreateRequest commentCreateRequest, Principal principal) {
        CommentCreateServiceDto serviceDto = new CommentCreateServiceDto(commentCreateRequest);
        saveCommentUseCase.save(principal.getName(), postId, serviceDto);

        return "redirect:/posts/" + postId;
    }

    // 댓글 삭제
    @PostMapping("/posts/{postId}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable(name = "postId") Long postId,
        @PathVariable(name = "commentId") Long commentId, Principal principal) {
        deleteCommentUseCase.delete(postId, commentId, principal.getName());

        return "redirect:/posts/" + postId;
    }

    // 댓글 수정
    @PostMapping("/posts/{postId}/comments/{commentId}/update")
    public String updateComment(@PathVariable(name = "postId") Long postId,
        @PathVariable(name = "commentId") Long commentId,
        CommentUpdateRequest commentUpdateRequest, Principal principal) {
        CommentUpdateServiceDto serviceDto = new CommentUpdateServiceDto(commentUpdateRequest);
        updateCommentUseCase.update(postId, commentId, serviceDto,
            principal.getName());

        return "redirect:/posts/" + postId;
    }

    // 댓글 신고하기
    @PostMapping("/posts/{postId}/comments/{commentId}/declaration")
    public String inquiryComment(@PathVariable(name = "postId") Long postId,
        @PathVariable(name = "commentId") Long commentId) {
        declarationCommentUseCase.declaration(postId, commentId);

        return "redirect:/posts/" + postId;
    }

}
