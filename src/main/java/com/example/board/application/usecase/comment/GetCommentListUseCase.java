package com.example.board.application.usecase.comment;

import com.example.board.api.controller.comment.response.CommentListResponse;
import com.example.board.application.usecase.comment.dto.GetCommentListServiceRequest;
import java.util.List;

public interface GetCommentListUseCase {

    List<CommentListResponse> findCommentByPost(GetCommentListServiceRequest dto);

}
