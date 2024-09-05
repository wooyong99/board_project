package com.example.board.adapter.ports.out.persistence.post;


import static com.example.board.domain.entity.QComment.comment;
import static com.example.board.domain.entity.QPost.post;

import com.example.board.adapter.ports.in.dto.response.post.PostDetailResponse;
import com.example.board.adapter.ports.in.dto.response.post.PostListResponse;
import com.example.board.adapter.ports.in.dto.response.post.QPostDetailResponse;
import com.example.board.adapter.ports.in.dto.response.post.QPostListResponse;
import com.example.board.domain.entity.Comment;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomPostDaoImpl implements CustomPostDao {

    private final JPAQueryFactory queryFactory;

    @Override
    public PostDetailResponse findPost(Long postId) {
        PostDetailResponse response = queryFactory
            .select(
                new QPostDetailResponse(
                    post.id,
                    post.title,
                    post.content,
                    post.createdAt,
                    post.likes,
                    post.category.name,
                    post.member.email
                )
            ).from(post)
            .where(
                post.id.eq(postId),
                post.isDeleted.isFalse()
            )
            .fetchOne();

        List<Comment> comments = queryFactory.selectFrom(comment)
            .where(
                comment.post.id.eq(postId),
                comment.isDeleted.isFalse()
            )
            .fetch();

        response.setComments(comments);
        return response;
    }

    @Override
    public Page<PostListResponse> findPostList(Long categoryId, String keyword, Pageable pageable) {
        List<PostListResponse> fetch = queryFactory
            .select(
                new QPostListResponse(
                    post.id,
                    post.title,
                    post.content,
                    post.createdAt,
                    JPAExpressions
                        .select(comment.count())
                        .from(comment)
                        .where(
                            comment.post.id.eq(post.id),
                            comment.isDeleted.isFalse()
                        )
                )
            )
            .from(post)
            .orderBy(post.createdAt.desc())
            .where(
                categoryIdEq(categoryId),
                titleContains(keyword),
                post.isDeleted.isFalse()
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long count = findPostListForCount(categoryId, keyword);

        return new PageImpl<>(fetch, pageable, count);
    }

    private long findPostListForCount(Long categoryId, String keyword) {
        Long count = queryFactory
            .select(post.count())
            .from(post)
            .where(
                categoryIdEq(categoryId),
                post.isDeleted.isFalse()
            )
            .fetchOne();

        if (count == null) {
            count = 0L;
        }

        return count;
    }

    private BooleanExpression categoryIdEq(Long categoryId) {
        return categoryId != null ? post.category.id.eq(categoryId) : null;
    }

    private BooleanExpression titleContains(String keyword) {
        return keyword != null ? post.title.contains(keyword) : null;
    }
}
