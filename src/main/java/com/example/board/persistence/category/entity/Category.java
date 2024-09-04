package com.example.board.persistence.category.entity;

import com.example.board.persistence.model.BaseEntity;
import com.example.board.persistence.post.entity.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
public class Category extends BaseEntity {

    private String name;

    @ColumnDefault("false")
    private boolean isDeleted;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST,
        CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    protected Category() {
    }
}
