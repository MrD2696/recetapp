package com.recetapp.recetapp.dto;

/**
 * DTO for login requests.
 * Contains username and password for authentication.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public class LoginRequest {
    
    private String username;
    private String password;
    private String appVersion;
    
    // Default constructor
    public LoginRequest() {}
    
    // Constructor with parameters
    public LoginRequest(String username, String password, String appVersion) {
        this.username = username;
        this.password = password;
        this.appVersion = appVersion;
    }
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
}
