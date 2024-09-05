package com.example.board.adapter.ports.out.persistence.category;


import com.example.board.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, Long> {

}
