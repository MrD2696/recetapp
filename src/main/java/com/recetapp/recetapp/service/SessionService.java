package com.recetapp.recetapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recetapp.recetapp.constants.LogConstants;
import com.recetapp.recetapp.model.User;
import com.recetapp.recetapp.model.UserSession;
import com.recetapp.recetapp.repository.UserSessionRepository;

/**
 * Service class for managing user sessions.
 * This class provides methods for session management including
 * login, logout, and session validation with a maximum of 2 active sessions per user.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Service
public class SessionService {
    
    private static final int MAX_SESSIONS_PER_USER = 2;
    
    @Autowired
    private UserSessionRepository userSessionRepository;
    
    /**
     * Creates a new session for a user, enforcing the maximum session limit.
     * If the user already has 2 active sessions, the oldest one is terminated.
     * 
     * @param user the user to create a session for
     * @param ipAddress the IP address of the session
     * @param userAgent the user agent string
     * @return the created UserSession
     */
    public UserSession createSession(User user, String ipAddress, String userAgent) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.SESSION_SERVICE_PREFIX, "Creating session for user: " + user.getUsername()));
        
        // Check current active sessions
        long activeSessionCount = userSessionRepository.countByUserIdAndIsActiveTrue(user.getUserId());
        System.out.println(LogConstants.buildLogMessage(LogConstants.SESSION_SERVICE_PREFIX, "User has " + activeSessionCount + " active sessions"));
        
        // If user has reached the limit, terminate the oldest session
        if (activeSessionCount >= MAX_SESSIONS_PER_USER) {
            System.out.println(LogConstants.buildLogMessage(LogConstants.SESSION_SERVICE_PREFIX, "Maximum sessions reached, terminating oldest session"));
            terminateOldestSession(user.getUserId());
        }
        
        // Generate unique session token
        String sessionToken = generateSessionToken();
        
        // Create new session
        UserSession newSession = new UserSession(
            user.getUserId(),
            user.getUsername(),
            sessionToken,
            ipAddress,
            userAgent
        );
        
        UserSession savedSession = userSessionRepository.save(newSession);
        System.out.println(LogConstants.buildLogMessage(LogConstants.SESSION_SERVICE_PREFIX, "New session created with ID: " + savedSession.getSessionId()));
        
        return savedSession;
    }
    
    /**
     * Terminates a specific session by setting it as inactive.
     * 
     * @param sessionToken the session token to terminate
     * @return true if session was terminated, false if not found
     */
    public boolean terminateSession(String sessionToken) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.SESSION_SERVICE_PREFIX, "Terminating session with token: " + sessionToken));
        
        UserSession session = userSessionRepository.findBySessionTokenAndIsActiveTrue(sessionToken);
        if (session != null) {
            session.setIsActive(false);
            session.setLastActivity(LocalDateTime.now());
            userSessionRepository.save(session);
            System.out.println(LogConstants.buildLogMessage(LogConstants.SESSION_SERVICE_PREFIX, "Session terminated successfully"));
            return true;
        }
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.SESSION_SERVICE_PREFIX, "Session not found or already inactive"));
        return false;
    }
    
    /**
     * Terminates all active sessions for a specific user.
     * 
     * @param userId the user ID whose sessions should be terminated
     */
    public void terminateAllUserSessions(Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.SESSION_SERVICE_PREFIX, "Terminating all sessions for user ID: " + userId));
        
        List<UserSession> activeSessions = userSessionRepository.findByUserIdAndIsActiveTrue(userId);
        for (UserSession session : activeSessions) {
            session.setIsActive(false);
            session.setLastActivity(LocalDateTime.now());
        }
        userSessionRepository.saveAll(activeSessions);
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.SESSION_SERVICE_PREFIX, "Terminated " + activeSessions.size() + " sessions"));
    }
    
    /**
     * Terminates the oldest active session for a user.
     * 
     * @param userId the user ID
     */
    private void terminateOldestSession(Integer userId) {
        List<UserSession> oldestSessions = userSessionRepository.findOldestActiveSessionsByUserId(userId);
        if (!oldestSessions.isEmpty()) {
            UserSession oldestSession = oldestSessions.get(0);
            oldestSession.setIsActive(false);
            oldestSession.setLastActivity(LocalDateTime.now());
            userSessionRepository.save(oldestSession);
            System.out.println(LogConstants.buildLogMessage(LogConstants.SESSION_SERVICE_PREFIX, "Oldest session terminated: " + oldestSession.getSessionId()));
        }
    }
    
    /**
     * Validates if a session token is valid and active.
     * 
     * @param sessionToken the session token to validate
     * @return true if valid and active, false otherwise
     */
    public boolean isValidSession(String sessionToken) {
        UserSession session = userSessionRepository.findBySessionTokenAndIsActiveTrue(sessionToken);
        if (session != null) {
            // Update last activity
            session.setLastActivity(LocalDateTime.now());
            userSessionRepository.save(session);
            return true;
        }
        return false;
    }
    
    /**
     * Gets all active sessions for a user.
     * 
     * @param userId the user ID
     * @return list of active sessions
     */
    public List<UserSession> getUserActiveSessions(Integer userId) {
        return userSessionRepository.findByUserIdAndIsActiveTrue(userId);
    }
    
    /**
     * Generates a unique session token.
     * 
     * @return a unique session token
     */
    private String generateSessionToken() {
        return UUID.randomUUID().toString().replace("-", "") + "_" + System.currentTimeMillis();
    }
    
    /**
     * Cleans up inactive sessions older than the specified time.
     * 
     * @param cutoffTime the cutoff time for inactive sessions
     */
    public void cleanupInactiveSessions(LocalDateTime cutoffTime) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.SESSION_SERVICE_PREFIX, "Cleaning up inactive sessions older than: " + cutoffTime));
        
        List<UserSession> inactiveSessions = userSessionRepository.findInactiveSessions(cutoffTime);
        for (UserSession session : inactiveSessions) {
            session.setIsActive(false);
        }
        userSessionRepository.saveAll(inactiveSessions);
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.SESSION_SERVICE_PREFIX, "Cleaned up " + inactiveSessions.size() + " inactive sessions"));
    }
}
