package com.recetapp.recetapp.dto;

import java.time.LocalDateTime;

/**
 * DTO for session information.
 * Contains details about active user sessions.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public class SessionInfo {
    
    private String sessionId;
    private String username;
    private LocalDateTime loginTime;
    private String ipAddress;
    private String userAgent;
    
    // Default constructor
    public SessionInfo() {}
    
    // Constructor with parameters
    public SessionInfo(String sessionId, String username, LocalDateTime loginTime, String ipAddress, String userAgent) {
        this.sessionId = sessionId;
        this.username = username;
        this.loginTime = loginTime;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
    }
    
    // Getters and Setters
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public LocalDateTime getLoginTime() {
        return loginTime;
    }
    
    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public String getUserAgent() {
        return userAgent;
    }
    
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
