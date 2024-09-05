package com.example.board.infrastructure.persistence.inquiry;


import static com.example.board.domain.entity.QInquiry.inquiry;

import com.example.board.adapter.ports.in.dto.response.post.inquiry.InquiryListResponse;
import com.example.board.adapter.ports.in.dto.response.post.inquiry.QInquiryListResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomInquiryRepositoryImpl implements CustomInquiryRepository {

    private final JPAQueryFactory queryFactory;

//    @Autowired
//    public CustomInquiryRepositoryImpl(JPAQueryFactory queryFactory) {
//        this.queryFactory = queryFactory;
//    }


    @Override
    public Page<InquiryListResponse> findList(Pageable pageable) {
        List<InquiryListResponse> fetch = queryFactory.select(
                new QInquiryListResponse(
                    inquiry.id,
                    inquiry.member.nickname,
                    inquiry.member.email,
                    inquiry.createdAt,
                    inquiry.member.blockedAt,
                    inquiry.member.isBlock,
                    inquiry.member.id
                )
            )
            .from(inquiry)
            .where(
                inquiry.isDeleted.isFalse()
            )
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .orderBy(inquiry.createdAt.desc())
            .fetch();

        long count = findListForCount();

        return new PageImpl<>(fetch, pageable, count);
    }

    private long findListForCount() {
        Long count = queryFactory
            .select(inquiry.count())
            .from(inquiry)
            .where(inquiry.isDeleted.isFalse())
            .fetchOne();

        if (count == null) {
            count = 0L;
        }

        return count;
    }
}
