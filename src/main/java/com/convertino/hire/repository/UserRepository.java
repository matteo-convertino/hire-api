package com.convertino.hire.repository;

import com.convertino.hire.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.role = 'MODERATOR'")
    Optional<User> findModeratorByEmail(String email);
}