package com.hanghaemini4.jaranghalram.repository;

import com.hanghaemini4.jaranghalram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
