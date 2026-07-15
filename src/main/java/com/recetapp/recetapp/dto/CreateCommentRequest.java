package com.recetapp.recetapp.dto;

/**
 * DTO para solicitudes de creación de comentarios.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public class CreateCommentRequest {
    
    private Integer recipeId;
    private Integer userId;
    private String content;
    
    // Constructor por defecto
    public CreateCommentRequest() {}
    
    // Constructor con parámetros
    public CreateCommentRequest(Integer recipeId, Integer userId, String content) {
        this.recipeId = recipeId;
        this.userId = userId;
        this.content = content;
    }
    
    // Getters y Setters
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
    
    @Override
    public String toString() {
        return "CreateCommentRequest{" +
                "recipeId=" + recipeId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                '}';
    }
}
