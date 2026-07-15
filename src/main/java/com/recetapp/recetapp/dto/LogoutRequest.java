package com.recetapp.recetapp.dto;

/**
 * DTO for logout requests.
 * Contains username for logout operation.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public class LogoutRequest {
    
    private String username;
    
    // Default constructor
    public LogoutRequest() {}
    
    // Constructor with parameters
    public LogoutRequest(String username) {
        this.username = username;
    }
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
}
