package com.hanghaemini4.jaranghalram.repository;

import com.hanghaemini4.jaranghalram.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLIkeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByBoardIdAndUserId(Long postId, Long userId);
    void deleteBoardLikeByUserId(Long UserId);
}
