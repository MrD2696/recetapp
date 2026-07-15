package com.recetapp.recetapp.dto;

import java.time.LocalDateTime;

/**
 * DTO para representar un comentario.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public class CommentDTO {
    
    private Integer commentId;
    private Integer recipeId;
    private Integer userId;
    private String content;
    private LocalDateTime createdAt;
    
    // Información adicional del usuario (opcional)
    private String username;
    private String userEmail;
    
    // Información adicional de la receta (opcional)
    private String recipeTitle;
    
    // Constructor por defecto
    public CommentDTO() {}
    
    // Constructor con parámetros básicos
    public CommentDTO(Integer recipeId, Integer userId, String content) {
        this.recipeId = recipeId;
        this.userId = userId;
        this.content = content;
    }
    
    // Constructor completo
    public CommentDTO(Integer commentId, Integer recipeId, Integer userId, String content, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.recipeId = recipeId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
    }
    
    // Constructor con información de usuario
    public CommentDTO(Integer commentId, Integer recipeId, Integer userId, String content, LocalDateTime createdAt,
                      String username, String userEmail) {
        this(commentId, recipeId, userId, content, createdAt);
        this.username = username;
        this.userEmail = userEmail;
    }
    
    // Constructor con información de usuario y receta
    public CommentDTO(Integer commentId, Integer recipeId, Integer userId, String content, LocalDateTime createdAt,
                      String username, String userEmail, String recipeTitle) {
        this(commentId, recipeId, userId, content, createdAt, username, userEmail);
        this.recipeTitle = recipeTitle;
    }
    
    // Getters y Setters
    public Integer getCommentId() {
        return commentId;
    }
    
    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
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
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUserEmail() {
        return userEmail;
    }
    
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
    public String getRecipeTitle() {
        return recipeTitle;
    }
    
    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }
    
    @Override
    public String toString() {
        return "CommentDTO{" +
                "commentId=" + commentId +
                ", recipeId=" + recipeId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", username='" + username + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", recipeTitle='" + recipeTitle + '\'' +
                '}';
    }
}
