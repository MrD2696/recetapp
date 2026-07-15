package com.recetapp.recetapp.dto;

import java.time.LocalDateTime;

/**
 * DTO optimizado para mostrar información resumida de usuarios.
 * Evita el problema N+1 al cargar solo los datos necesarios.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public class UserSummaryDTO {
    
    private Integer userId;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private Boolean online;
    
    // Constructor por defecto
    public UserSummaryDTO() {}
    
    // Constructor con parámetros
    public UserSummaryDTO(Integer userId, String username, String email, 
                         LocalDateTime createdAt, LocalDateTime lastLogin, Boolean online) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
        this.online = online;
    }
    
    // Getters y Setters
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
    
    @Override
    public String toString() {
        return "UserSummaryDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", lastLogin=" + lastLogin +
                ", online=" + online +
                '}';
    }
}
