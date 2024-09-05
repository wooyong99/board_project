package com.example.board.infrastructure.persistence.post;

import com.example.board.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, Long> {

    Page<Post> findByCategoryIdOrderByCreatedAtDesc(Long categoryId, Pageable pageable);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
