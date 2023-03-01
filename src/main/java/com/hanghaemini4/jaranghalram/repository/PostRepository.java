package com.hanghaemini4.jaranghalram.repository;

import com.hanghaemini4.jaranghalram.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserId(Long userId);
    @Modifying
    @Query("UPDATE Post p SET p.postLikeCount = :postLikeCount WHERE p.id = :postId")
    void updatePostLikeCount(@Param("postId") Long postId, @Param("postLikeCount") int postLikeCount);
}
