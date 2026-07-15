package com.recetapp.recetapp.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for User entity.
 * This class provides methods to convert User entities to/from UserDTO objects.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public class UserDTO {
    
    private Integer userId;
    private String username;
    private String password; // Campo para password en creación/actualización
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private Boolean online; // Campo para estado online
    private String confirmPassword; // Campo para confirmar contraseña
    
    // Default constructor
    public UserDTO() {}
    
    // Constructor with basic parameters
    public UserDTO(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    // Full constructor
    public UserDTO(Integer userId, String username, String email, 
                   LocalDateTime createdAt, LocalDateTime lastLogin, Boolean online) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
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
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
