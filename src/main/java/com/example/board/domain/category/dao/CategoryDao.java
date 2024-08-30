package com.example.board.domain.category.dao;

import com.example.board.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, Long> {

}
