package com.example.board.adapter.out.persistence.category;

import com.example.board.domain.entity.Category;
import com.example.board.infrastructure.exception.NotFoundCategoryException;
import com.example.board.infrastructure.persistence.category.CategoryJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryRepositoryAdapter {

    private final CategoryJpaRepository categoryJpaRepository;

    @Autowired
    public CategoryRepositoryAdapter(CategoryJpaRepository categoryJpaRepository) {
        this.categoryJpaRepository = categoryJpaRepository;
    }

    public Category findById(Long categoryId) {
        return categoryJpaRepository.findById(categoryId).orElseThrow(
            () -> new NotFoundCategoryException("존재하지 않는 카테고리입니다.")
        );
    }
}
