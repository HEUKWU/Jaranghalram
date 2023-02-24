package com.hanghaemini4.jaranghalram.repository;

import com.hanghaemini4.jaranghalram.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
