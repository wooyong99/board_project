package com.example.board.infrastructure.persistence.category;

import com.example.board.domain.entity.Category;
import com.example.board.domain.repository.CategoryRepository;
import com.example.board.exception.NotFoundCategoryException;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;
    private final CustomCategoryRepository customCategoryRepository;

    public CategoryRepositoryImpl(CategoryJpaRepository categoryJpaRepository,
        CustomCategoryRepository customCategoryRepository) {
        this.categoryJpaRepository = categoryJpaRepository;
        this.customCategoryRepository = customCategoryRepository;
    }

    public Category findById(Long categoryId) {
        return categoryJpaRepository.findById(categoryId).orElseThrow(
            () -> new NotFoundCategoryException("존재하지 않는 카테고리입니다.")
        );
    }

}
