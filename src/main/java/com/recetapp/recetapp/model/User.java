package com.recetapp.recetapp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity class for User table.
 * This class represents the user entity and its mapping to the database table.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Entity
@Table(name = "Users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false)
    private Integer userId;
    
    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String username;
    
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    
    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;
    
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "lastLogin")
    private LocalDateTime lastLogin;
    
    @Column(name = "online", nullable = false)
    private Boolean online = false; // Default value: offline
    
    @Column(name = "AppVersion", length = 50)
    private String appVersion;
    
    @Column(name = "code", length = 10)
    private String code;

    @Column(name = "validateUser", nullable = false)
    private Boolean validateUser = false;
    
    @Column(name = "new")
    private Integer isNew;
    
    // Default constructor
    public User() {
        this.createdAt = LocalDateTime.now();
    }
    
    // Constructor with basic parameters
    public User(String username, String passwordHash, String email) {
        this.username = username;
        this.password = passwordHash;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }
    
    // Full constructor
    public User(Integer userId, String username, String passwordHash, String email, 
                LocalDateTime createdAt, LocalDateTime lastLogin, Boolean online) {
        this.userId = userId;
        this.username = username;
        this.password = passwordHash;
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
    
    public String getPasswordHash() {
        return password;
    }
    
    public void setPasswordHash(String password) {
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

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getValidateUser() {
        return validateUser;
    }

    public void setValidateUser(Boolean validateUser) {
        this.validateUser = validateUser;
    }
    
    public Integer getIsNew() {
        return isNew;
    }
    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }
    
    // Relaciones de Followers
    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY)
    private List<UserFollower> following = new ArrayList<>();
    
    @OneToMany(mappedBy = "followed", fetch = FetchType.LAZY)
    private List<UserFollower> followers = new ArrayList<>();
    
    // Métodos para manejar followers
    public List<UserFollower> getFollowing() {
        return following;
    }
    
    public void setFollowing(List<UserFollower> following) {
        this.following = following;
    }
    
    public List<UserFollower> getFollowers() {
        return followers;
    }
    
    public void setFollowers(List<UserFollower> followers) {
        this.followers = followers;
    }
    
    // Métodos de conveniencia
    public int getFollowersCount() {
        return followers != null ? followers.size() : 0;
    }
    
    public int getFollowingCount() {
        return following != null ? following.size() : 0;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", lastLogin=" + lastLogin +
                ", online=" + online +
                ", appVersion='" + appVersion + '\'' +
                '}';
    }
}
