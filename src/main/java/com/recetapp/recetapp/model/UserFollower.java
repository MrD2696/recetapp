package com.recetapp.recetapp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Modelo para manejar las relaciones de seguimiento entre usuarios.
 * Un usuario puede seguir a otros usuarios y ser seguido por otros.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Entity
@Table(
    name = "UserFollowers",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"follower_id", "followed_id"},
            name = "UC_UserFollowers"
        )
    }
)
public class UserFollower {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;  // Usuario que sigue
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followed_id", nullable = false)
    private User followed;  // Usuario que es seguido
    
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;
    
    // Constructores
    public UserFollower() {
        this.createdAt = LocalDateTime.now();
    }
    
    public UserFollower(User follower, User followed) {
        this.follower = follower;
        this.followed = followed;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getFollower() {
        return follower;
    }
    
    public void setFollower(User follower) {
        this.follower = follower;
    }
    
    public User getFollowed() {
        return followed;
    }
    
    public void setFollowed(User followed) {
        this.followed = followed;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "UserFollower{" +
                "id=" + id +
                ", follower=" + (follower != null ? follower.getUsername() : "null") +
                ", followed=" + (followed != null ? followed.getUsername() : "null") +
                ", createdAt=" + createdAt +
                '}';
    }
}
