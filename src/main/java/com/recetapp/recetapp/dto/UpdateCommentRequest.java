package com.recetapp.recetapp.dto;

/**
 * DTO para solicitudes de actualización de comentarios.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public class UpdateCommentRequest {
    
    private Integer userId;
    private String content;
    
    // Constructor por defecto
    public UpdateCommentRequest() {}
    
    // Constructor con parámetros
    public UpdateCommentRequest(Integer userId, String content) {
        this.userId = userId;
        this.content = content;
    }
    
    // Getters y Setters
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
        return "UpdateCommentRequest{" +
                "userId=" + userId +
                ", content='" + content + '\'' +
                '}';
    }
}
