package com.recetapp.recetapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.recetapp.recetapp.model.UserSession;

/**
 * Repository interface for UserSession entity.
 * This class provides methods to interact with the database for UserSession entity.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Integer> {
    
    // Find all active sessions for a specific user
    List<UserSession> findByUserIdAndIsActiveTrue(Integer userId);
    
    // Find all active sessions for a specific username
    List<UserSession> findByUsernameAndIsActiveTrue(String username);
    
    // Count active sessions for a specific user
    long countByUserIdAndIsActiveTrue(Integer userId);
    
    // Count active sessions for a specific username
    long countByUsernameAndIsActiveTrue(String username);
    
    // Find session by token
    UserSession findBySessionTokenAndIsActiveTrue(String sessionToken);
    
    // Find oldest active session for a user
    @Query("SELECT us FROM UserSession us WHERE us.userId = :userId AND us.isActive = true ORDER BY us.loginTime ASC")
    List<UserSession> findOldestActiveSessionsByUserId(@Param("userId") Integer userId);
    
    // Find sessions older than specified time
    @Query("SELECT us FROM UserSession us WHERE us.lastActivity < :cutoffTime AND us.isActive = true")
    List<UserSession> findInactiveSessions(@Param("cutoffTime") java.time.LocalDateTime cutoffTime);
    
    // Find ALL sessions for a specific user (active and inactive)
    List<UserSession> findByUserId(Integer userId);
    
    // Count ALL sessions for a specific user (active and inactive)
    long countByUserId(Integer userId);
}
