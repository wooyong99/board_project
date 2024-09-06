package com.example.board.api.controller.comment;

import com.example.board.api.controller.comment.request.CommentCreateRequest;
import com.example.board.api.controller.comment.request.CommentUpdateRequest;
import com.example.board.application.usecase.comment.DeclarationCommentUseCase;
import com.example.board.application.usecase.comment.DeleteCommentUseCase;
import com.example.board.application.usecase.comment.SaveCommentUseCase;
import com.example.board.application.usecase.comment.UpdateCommentUseCase;
import com.example.board.application.usecase.comment.dto.DeclarationCommentServiceDto;
import com.example.board.application.usecase.comment.dto.DeleteCommentServiceDto;
import com.example.board.application.usecase.comment.dto.SaveCommentServiceDto;
import com.example.board.application.usecase.comment.dto.UpdateCommentServiceDto;
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
        SaveCommentServiceDto serviceDto = new SaveCommentServiceDto(principal.getName(), postId,
            commentCreateRequest.getContent());
        saveCommentUseCase.save(serviceDto);

        return "redirect:/posts/" + postId;
    }

    // 댓글 삭제
    @PostMapping("/posts/{postId}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable(name = "postId") Long postId,
        @PathVariable(name = "commentId") Long commentId, Principal principal) {
        DeleteCommentServiceDto serviceDto = new DeleteCommentServiceDto(postId, commentId,
            principal.getName());
        deleteCommentUseCase.delete(serviceDto);

        return "redirect:/posts/" + postId;
    }

    // 댓글 수정
    @PostMapping("/posts/{postId}/comments/{commentId}/update")
    public String updateComment(@PathVariable(name = "postId") Long postId,
        @PathVariable(name = "commentId") Long commentId,
        CommentUpdateRequest commentUpdateRequest, Principal principal) {
        UpdateCommentServiceDto serviceDto = new UpdateCommentServiceDto(postId, commentId,
            principal.getName(), commentUpdateRequest.getContent());
        updateCommentUseCase.update(serviceDto);

        return "redirect:/posts/" + postId;
    }

    // 댓글 신고하기
    @PostMapping("/posts/{postId}/comments/{commentId}/declaration")
    public String inquiryComment(@PathVariable(name = "postId") Long postId,
        @PathVariable(name = "commentId") Long commentId) {
        DeclarationCommentServiceDto serviceDto = new DeclarationCommentServiceDto(postId,
            commentId);
        declarationCommentUseCase.declaration(serviceDto);

        return "redirect:/posts/" + postId;
    }
}
