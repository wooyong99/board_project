package com.example.board.api.controller.comment;

import com.example.board.api.controller.comment.request.CommentCreateRequest;
import com.example.board.api.controller.comment.request.CommentUpdateRequest;
import com.example.board.api.controller.comment.response.CommentListResponse;
import com.example.board.application.usecase.comment.DeclarationCommentUseCase;
import com.example.board.application.usecase.comment.DeleteCommentUseCase;
import com.example.board.application.usecase.comment.GetCommentListUseCase;
import com.example.board.application.usecase.comment.SaveCommentUseCase;
import com.example.board.application.usecase.comment.UpdateCommentUseCase;
import com.example.board.application.usecase.comment.dto.DeclarationCommentServiceDto;
import com.example.board.application.usecase.comment.dto.DeleteCommentServiceDto;
import com.example.board.application.usecase.comment.dto.GetCommentListServiceRequest;
import com.example.board.application.usecase.comment.dto.SaveCommentServiceDto;
import com.example.board.application.usecase.comment.dto.UpdateCommentServiceDto;
import com.example.board.domain.entity.RoleEnum;
import com.example.board.infrastructure.aop.AuthorizationRequired;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    private final SaveCommentUseCase saveCommentUseCase;
    private final GetCommentListUseCase getCommentListUseCase;
    private final DeleteCommentUseCase deleteCommentUseCase;
    private final DeclarationCommentUseCase declarationCommentUseCase;
    private final UpdateCommentUseCase updateCommentUseCase;

    @Autowired
    public CommentController(SaveCommentUseCase saveCommentUseCase,
        GetCommentListUseCase getCommentListUseCase,
        DeleteCommentUseCase deleteCommentUseCase,
        DeclarationCommentUseCase declarationCommentUseCase,
        UpdateCommentUseCase updateCommentUseCase) {
        this.saveCommentUseCase = saveCommentUseCase;
        this.getCommentListUseCase = getCommentListUseCase;
        this.deleteCommentUseCase = deleteCommentUseCase;
        this.declarationCommentUseCase = declarationCommentUseCase;
        this.updateCommentUseCase = updateCommentUseCase;
    }

    // 댓글 리스트 조회
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity getCommentByPost(@PathVariable(name = "postId") Long postId) {
        GetCommentListServiceRequest serviceDto = new GetCommentListServiceRequest(postId);
        List<CommentListResponse> response = getCommentListUseCase.findCommentByPost(
            serviceDto);
        return ResponseEntity.ok(response);
    }

    // 댓글 저장
    @PostMapping("/posts/{postId}/comments")
    @AuthorizationRequired({RoleEnum.EXERCISE_ADMIN, RoleEnum.USER})
    public ResponseEntity registerComment(@PathVariable(name = "postId") Long postId,
        @RequestBody CommentCreateRequest commentCreateRequest, Principal principal) {
        SaveCommentServiceDto serviceDto = new SaveCommentServiceDto(
            Long.parseLong(principal.getName()), postId,
            commentCreateRequest.getContent());
        saveCommentUseCase.save(serviceDto);
        return ResponseEntity.ok().build();
    }

    // 댓글 삭제
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    @AuthorizationRequired({RoleEnum.EXERCISE_ADMIN, RoleEnum.USER})
    public ResponseEntity deleteComment(@PathVariable(name = "postId") Long postId,
        @PathVariable(name = "commentId") Long commentId, Principal principal) {
        DeleteCommentServiceDto serviceDto = new DeleteCommentServiceDto(postId, commentId,
            Long.parseLong(principal.getName()));
        deleteCommentUseCase.delete(serviceDto);

        return ResponseEntity.ok().build();
    }

    // 댓글 수정
    @PutMapping("/posts/{postId}/comments/{commentId}")
    @AuthorizationRequired({RoleEnum.EXERCISE_ADMIN, RoleEnum.USER})
    public ResponseEntity updateComment(@PathVariable(name = "postId") Long postId,
        @PathVariable(name = "commentId") Long commentId,
        @RequestBody CommentUpdateRequest commentUpdateRequest, Principal principal) {
        UpdateCommentServiceDto serviceDto = new UpdateCommentServiceDto(postId, commentId,
            Long.parseLong(principal.getName()), commentUpdateRequest.getContent());
        updateCommentUseCase.update(serviceDto);

        return ResponseEntity.noContent().build();
    }

    // 댓글 신고하기
    @PostMapping("/posts/{postId}/comments/{commentId}/declaration")
    @AuthorizationRequired({RoleEnum.EXERCISE_ADMIN, RoleEnum.USER})
    public ResponseEntity inquiryComment(@PathVariable(name = "postId") Long postId,
        @PathVariable(name = "commentId") Long commentId) {
        DeclarationCommentServiceDto serviceDto = new DeclarationCommentServiceDto(postId,
            commentId);
        declarationCommentUseCase.declaration(serviceDto);

        return ResponseEntity.ok().build();
    }
}
