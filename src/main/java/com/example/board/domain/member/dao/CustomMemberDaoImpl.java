package com.example.board.domain.member.dao;

import static com.example.board.domain.member.entity.QMember.member;

import com.example.board.domain.member.dto.MemberInfoResponse;
import com.example.board.domain.member.dto.QMemberInfoResponse;
import com.example.board.domain.member.entity.MemberRoleEnum;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class CustomMemberDaoImpl implements CustomMemberDao {

    private final JPAQueryFactory queryFactory;

    public CustomMemberDaoImpl(JPAQueryFactory queryFactory) {
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
                    member.role,
                    member.isBlock
                )
            )
            .from(member)
            .where(
                member.role.eq(MemberRoleEnum.USER),
                emailContains(keyword)
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long count = searchForCount();

        return new PageImpl<>(fetch, pageable, count);
    }

    private long searchForCount() {
        Long count = queryFactory.select(member.count())
            .from(member)
            .where(member.role.eq(MemberRoleEnum.USER))
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
