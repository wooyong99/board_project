package com.example.board.domain.comment.dao;

import com.example.board.domain.comment.entity.Comment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDao extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndPostId(Long commentId, Long postId);
}
