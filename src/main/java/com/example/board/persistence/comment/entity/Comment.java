package com.example.board.persistence.comment.entity;

import com.example.board.persistence.member.entity.Member;
import com.example.board.persistence.model.BaseEntity;
import com.example.board.persistence.post.entity.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
public class Comment extends BaseEntity {

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Post post;

    @ColumnDefault("false")
    private boolean isDeleted;

    protected Comment() {
    }

    public Comment(String content, Post post, Member member) {
        this.content = content;
        this.member = member;
        setPost(post);
    }

    public static Comment create(String content, Post post, Member member) {
        return new Comment(content, post, member);
    }

    private void setPost(Post post) {
        if (this.post != null) {
            this.post.getComments().remove(this);
        }
        this.post = post;
        this.post.getComments().add(this);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
