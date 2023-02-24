package com.hanghaemini4.jaranghalram.repository;

import com.hanghaemini4.jaranghalram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByUserNickName(String userNickName);
}