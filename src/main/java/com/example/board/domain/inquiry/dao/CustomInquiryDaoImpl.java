package com.example.board.domain.inquiry.dao;

import static com.example.board.domain.inquiry.entity.QInquiry.inquiry;

import com.example.board.domain.inquiry.dto.InquiryListResponse;
import com.example.board.domain.inquiry.dto.QInquiryListResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomInquiryDaoImpl implements CustomInquiryDao {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<InquiryListResponse> findList(Pageable pageable) {
        List<InquiryListResponse> fetch = queryFactory.select(
                new QInquiryListResponse(
                    inquiry.id,
                    inquiry.member.nickname,
                    inquiry.member.email,
                    inquiry.createdAt,
                    inquiry.member.isBlock
                )
            )
            .from(inquiry)
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

        long count = findListForCount();

        return new PageImpl<>(fetch, pageable, count);
    }

    private long findListForCount() {
        Long count = queryFactory
            .select(inquiry.count())
            .from(inquiry)
            .fetchOne();

        if (count == null) {
            count = 0L;
        }

        return count;
    }
}
