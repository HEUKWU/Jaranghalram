package com.hanghaemini4.jaranghalram.repository;

import com.hanghaemini4.jaranghalram.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndUserId(Long id, Long userId);
}
