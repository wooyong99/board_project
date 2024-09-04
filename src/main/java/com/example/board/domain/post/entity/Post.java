package com.example.board.domain.post.entity;

import com.example.board.domain.category.entity.Category;
import com.example.board.domain.comment.entity.Comment;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.model.BaseEntity;
import com.example.board.domain.post.dto.PostUpdateServiceDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
public class Post extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ColumnDefault("0")
    @Min(0)
    private long likes;

    @ColumnDefault("false")
    private boolean isDeleted;

    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST,
        CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Category category;

    protected Post() {
    }

    public static Post create(String title, String content, Member member, Category category) {
        return new Post(title, content, member, category);
    }

    public Post(String title, String content, Member member, Category category) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.category = category;
    }

    public void update(PostUpdateServiceDto request) {
        if (!request.getTitle().equals(this.title)) {
            this.title = request.getTitle();
        }
        if (!request.getContent().equals(this.content)) {
            this.content = request.getContent();
        }
    }

    public void increaseLike() {
        this.likes++;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
