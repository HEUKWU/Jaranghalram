package com.hanghaemini4.jaranghalram.repository;


import com.hanghaemini4.jaranghalram.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUserNickName(String userNickName);
}