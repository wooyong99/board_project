package com.example.board.persistence.comment.dao;

import com.example.board.persistence.comment.entity.Comment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDao extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndPostId(Long commentId, Long postId);
}
