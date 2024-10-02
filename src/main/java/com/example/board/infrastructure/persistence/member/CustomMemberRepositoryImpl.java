package com.example.board.infrastructure.persistence.member;


import static com.example.board.domain.entity.QMember.member;

import com.example.board.api.controller.member.response.MemberInfoResponse;
import com.example.board.api.controller.member.response.QMemberInfoResponse;
import com.example.board.domain.entity.RoleEnum;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class CustomMemberRepositoryImpl implements CustomMemberRepository {

    private final JPAQueryFactory queryFactory;

    public CustomMemberRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<MemberInfoResponse> search(String keyword, Pageable pageable) {
        List<MemberInfoResponse> fetch = queryFactory.select(
                new QMemberInfoResponse(
                    member.id,
                    member.nickname,
                    member.email,
                    member.createdAt,
                    member.roles.get(0),
                    member.isBlock
                )
            )
            .from(member)
            .where(
                member.roles.get(0).role.name.eq(RoleEnum.USER),
                emailContains(keyword),
                member.isDeleted.isFalse()
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(member.createdAt.desc())
            .fetch();

        long count = searchForCount(keyword);

        return new PageImpl<>(fetch, pageable, count);
    }

    private long searchForCount(String keyword) {
        Long count = queryFactory.select(member.count())
            .from(member)
            .where(
//                member.roles.contains(new MemberRole(RoleEnum.USER)),
                emailContains(keyword),
                member.isDeleted.isFalse()
            )
            .fetchOne();

        if (count == null) {
            count = 0L;
        }

        return count;
    }

    private BooleanExpression emailContains(String keyword) {
        return keyword != null ? member.email.contains(keyword) : null;
    }
}
