package com.example.board.persistence.category.dao;

import com.example.board.persistence.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, Long> {

}
