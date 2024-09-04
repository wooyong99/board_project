package com.example.board.persistence.entity;

import com.example.board.persistence.inquiry.dto.InquiryDetailResponse;
import com.example.board.persistence.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
public class Inquiry extends BaseEntity {

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(unique = true, nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @ColumnDefault("false")
    private boolean isDeleted;

    protected Inquiry() {
    }

    @Builder
    public Inquiry(String content, Member member) {
        this.content = content;
        this.member = member;
    }

    public static Inquiry create(String content, Member member) {
        return new Inquiry(content, member);
    }

    public InquiryDetailResponse toDetailResponse() {
        return InquiryDetailResponse.builder()
            .id(this.getId())
            .content(this.content)
            .createdAt(this.getCreatedAt())
            .author(this.member.getEmail())
            .build();
    }

    public void delete() {
        this.isDeleted = true;
    }
}
