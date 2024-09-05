package com.example.board.infrastructure.persistence.comment;

import com.example.board.domain.entity.Comment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndPostId(Long commentId, Long postId);
}
