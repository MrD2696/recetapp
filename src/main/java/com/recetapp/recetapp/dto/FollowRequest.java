package com.recetapp.recetapp.dto;

import org.springframework.lang.NonNull;

/**
 * DTO para solicitudes de seguimiento entre usuarios.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public class FollowRequest {
    
    @NonNull
    private Integer followedUserId;
    
    // Constructor por defecto
    public FollowRequest() {}
    
    // Constructor con parámetros
    public FollowRequest(Integer followedUserId) {
        this.followedUserId = followedUserId;
    }
    
    // Getters y Setters
    public Integer getFollowedUserId() {
        return followedUserId;
    }
    
    public void setFollowedUserId(Integer followedUserId) {
        this.followedUserId = followedUserId;
    }
    
    @Override
    public String toString() {
        return "FollowRequest{" +
                "followedUserId=" + followedUserId +
                '}';
    }
}
