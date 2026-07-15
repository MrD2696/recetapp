package com.recetapp.recetapp.dto;

import java.time.LocalDateTime;

/**
 * DTO for login responses.
 * Contains user information and authentication status.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public class LoginResponse {
    
    private Integer userId;
    private String username;
    private String email;
    private LocalDateTime lastLogin;
    private Boolean online; // Estado online del usuario
    private String token; // Para futuras implementaciones de JWT
    
    // Default constructor
    public LoginResponse() {}
    
    // Constructor with parameters
    public LoginResponse(Integer userId, String username, String email, LocalDateTime lastLogin, Boolean online) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.lastLogin = lastLogin;
        this.online = online;
    }
    
    // Getters and Setters
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }
    
    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
    
    public Boolean getOnline() {
        return online;
    }
    
    public void setOnline(Boolean online) {
        this.online = online;
    }
    
    public Boolean isOnline() {
        return online;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
}
