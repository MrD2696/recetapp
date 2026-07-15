package com.recetapp.recetapp.dto;

import java.time.LocalDateTime;

/**
 * DTO para mostrar el perfil de usuario con información de followers.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public class UserProfileDTO {
    
    private Integer userId;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private Boolean online;
    private int followersCount;
    private int followingCount;
    private boolean isFollowing; // Si el usuario actual sigue a este usuario
    
    // Constructor por defecto
    public UserProfileDTO() {}
    
    // Constructor con parámetros
    public UserProfileDTO(Integer userId, String username, String email, 
                         LocalDateTime createdAt, LocalDateTime lastLogin, 
                         Boolean online, int followersCount, int followingCount) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
        this.online = online;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
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
    
    public int getFollowersCount() {
        return followersCount;
    }
    
    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }
    
    public int getFollowingCount() {
        return followingCount;
    }
    
    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }
    
    public boolean isFollowing() {
        return isFollowing;
    }
    
    public void setFollowing(boolean following) {
        isFollowing = following;
    }
    
    @Override
    public String toString() {
        return "UserProfileDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", lastLogin=" + lastLogin +
                ", online=" + online +
                ", followersCount=" + followersCount +
                ", followingCount=" + followingCount +
                ", isFollowing=" + isFollowing +
                '}';
    }
}
