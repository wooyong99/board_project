package com.example.board.domain.repository;

import com.example.board.domain.entity.Category;

public interface CategoryRepository {

    Category findById(Long categoryId);
}
