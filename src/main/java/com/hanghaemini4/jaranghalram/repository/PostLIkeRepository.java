package com.hanghaemini4.jaranghalram.repository;

import com.hanghaemini4.jaranghalram.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLIkeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByPostIdAndUserId(Long postId, Long userId);
    void deletePostLikeByUserId(Long UserId);

}
