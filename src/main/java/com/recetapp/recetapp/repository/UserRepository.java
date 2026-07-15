package com.recetapp.recetapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recetapp.recetapp.model.User;

/**
 * Repository interface for User entity.
 * This class provides methods to interact with the database for User entity.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    // Custom method to find by username
    User findByUsername(String username);
    
    // Custom method to check if exists by username
    boolean existsByUsername(String username);
    
    // Custom method to find by email
    User findByEmail(String email);
    
    // Custom method to check if exists by email
    boolean existsByEmail(String email);
}
