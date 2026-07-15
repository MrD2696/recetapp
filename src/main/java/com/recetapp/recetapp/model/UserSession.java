package com.recetapp.recetapp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity class for UserSession table.
 * This class represents active user sessions and their metadata.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Entity
@Table(name = "UserSessions")
public class UserSession {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sessionId", nullable = false)
    private Integer sessionId;
    
    @Column(name = "userId", nullable = false)
    private Integer userId;
    
    @Column(name = "username", nullable = false, length = 50)
    private String username;
    
    @Column(name = "sessionToken", nullable = false, length = 255, unique = true)
    private String sessionToken;
    
    @Column(name = "ipAddress", nullable = false, length = 45)
    private String ipAddress;
    
    @Column(name = "userAgent", nullable = false, length = 500)
    private String userAgent;
    
    @Column(name = "loginTime", nullable = false)
    private LocalDateTime loginTime;
    
    @Column(name = "lastActivity", nullable = false)
    private LocalDateTime lastActivity;
    
    @Column(name = "isActive", nullable = false)
    private Boolean isActive = true;
    
    // Default constructor
    public UserSession() {}
    
    // Constructor with parameters
    public UserSession(Integer userId, String username, String sessionToken, String ipAddress, String userAgent) {
        this.userId = userId;
        this.username = username;
        this.sessionToken = sessionToken;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.loginTime = LocalDateTime.now();
        this.lastActivity = LocalDateTime.now();
        this.isActive = true;
    }
    
    // Getters and Setters
    public Integer getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getSessionToken() {
        return sessionToken;
    }
    
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
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
    
    public LocalDateTime getLoginTime() {
        return loginTime;
    }
    
    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }
    
    public LocalDateTime getLastActivity() {
        return lastActivity;
    }
    
    public void setLastActivity(LocalDateTime lastActivity) {
        this.lastActivity = lastActivity;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public Boolean isActive() {
        return isActive;
    }
}
