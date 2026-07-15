package com.recetapp.recetapp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Modelo para representar los favoritos de los usuarios.
 * Un usuario puede marcar recetas como favoritas.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Entity
@Table(
    name = "Favorites",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"recipeId", "userId"},
            name = "UC_Favorites"
        )
    }
)
public class Favorite {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favoriteId")
    private Integer favoriteId;
    
    @Column(name = "recipeId", nullable = false)
    private Integer recipeId;
    
    @Column(name = "userId", nullable = false)
    private Integer userId;
    
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;
    
    // Relaciones con otras entidades (opcionales, para consultas)
    @ManyToOne
    @JoinColumn(name = "recipeId", insertable = false, updatable = false)
    private Recipe recipe;
    
    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;
    
    // Constructor por defecto
    public Favorite() {
        this.createdAt = LocalDateTime.now();
    }
    
    // Constructor con parámetros
    public Favorite(Integer recipeId, Integer userId) {
        this();
        this.recipeId = recipeId;
        this.userId = userId;
    }
    
    // Constructor completo
    public Favorite(Integer favoriteId, Integer recipeId, Integer userId, LocalDateTime createdAt) {
        this.favoriteId = favoriteId;
        this.recipeId = recipeId;
        this.userId = userId;
        this.createdAt = createdAt;
    }
    
    // Getters y Setters
    public Integer getFavoriteId() {
        return favoriteId;
    }
    
    public void setFavoriteId(Integer favoriteId) {
        this.favoriteId = favoriteId;
    }
    
    public Integer getRecipeId() {
        return recipeId;
    }
    
    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public Recipe getRecipe() {
        return recipe;
    }
    
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    @Override
    public String toString() {
        return "Favorite{" +
                "favoriteId=" + favoriteId +
                ", recipeId=" + recipeId +
                ", userId=" + userId +
                ", createdAt=" + createdAt +
                '}';
    }
}
