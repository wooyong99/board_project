package com.example.board.infrastructure.persistence.comment;


import static com.example.board.domain.entity.QComment.comment;

import com.example.board.api.controller.comment.response.CommentListResponse;
import com.example.board.api.controller.comment.response.QCommentListResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomCommentRepositoryImpl implements CustomCommentRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommentListResponse> findCommentByPost(Long postId) {
        List<CommentListResponse> fetch = queryFactory.select(
                new QCommentListResponse(
                    comment.id,
                    comment.content,
                    comment.member.email
                )
            ).from(comment)
            .where(comment.post.id.eq(postId))
            .where(comment.isDeleted.isFalse())
            .fetch();
        return fetch;
    }
}
