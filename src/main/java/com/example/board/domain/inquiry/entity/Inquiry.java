package com.example.board.domain.inquiry.entity;

import com.example.board.domain.inquiry.dto.InquiryDetailResponse;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inquiry extends BaseEntity {

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(unique = true, nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @Builder
    public Inquiry(String content, Member member) {
        this.content = content;
        this.member = member;
    }

    public InquiryDetailResponse toDetailResponse() {
        return InquiryDetailResponse.builder()
            .id(this.getId())
            .content(this.content)
            .createdAt(this.getCreatedAt())
            .author(this.member.getEmail())
            .build();
    }
}
