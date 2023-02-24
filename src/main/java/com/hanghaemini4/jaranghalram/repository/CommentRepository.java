package com.hanghaemini4.jaranghalram.repository;

import com.hanghaemini4.jaranghalram.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
