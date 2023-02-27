package com.hanghaemini4.jaranghalram.repository;

import com.hanghaemini4.jaranghalram.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByUserId(Long userId);
    List<Comment> findAllByPostId(Long postId);
}
