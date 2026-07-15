package com.recetapp.recetapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recetapp.recetapp.constants.LogConstants;
import com.recetapp.recetapp.constants.MessageConstants;
import com.recetapp.recetapp.dto.ApiResponse;
import com.recetapp.recetapp.dto.LoginRequest;
import com.recetapp.recetapp.dto.LoginResponse;
import com.recetapp.recetapp.dto.LogoutRequest;
import com.recetapp.recetapp.service.UserService;

/**
 * Controller for authentication operations.
 * This class handles login and other authentication-related endpoints.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Allow CORS for testing
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    // POST /api/auth/login - User login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "POST /api/auth/login - User login attempt"));
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Request body: username=" + loginRequest.getUsername()));
        
        // Get client information
        String ipAddress = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        String appVersion = loginRequest.getAppVersion();
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Client IP: " + ipAddress + ", User-Agent: " + userAgent + ", AppVersion: " + appVersion));
        
        try {
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Calling UserService.login()"));
            LoginResponse loginResponse = userService.login(loginRequest.getUsername(), loginRequest.getPassword(), ipAddress, userAgent, appVersion);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Login successful for user: " + loginResponse.getUsername()));
            ApiResponse<LoginResponse> response = ApiResponse.success(
                MessageConstants.LOGIN_SUCCESSFUL, 
                loginResponse, 
                HttpStatus.OK.value()
            );
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Sending 200 response for successful login"));
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Login failed: " + e.getMessage()));
            ApiResponse<LoginResponse> response = ApiResponse.error(
                e.getMessage(), 
                HttpStatus.UNAUTHORIZED.value()
            );
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Sending 401 response for failed login"));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Unexpected error during login: " + e.getMessage()));
            ApiResponse<LoginResponse> response = ApiResponse.error(
                MessageConstants.INTERNAL_SERVER_ERROR, 
                HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Sending 500 response for login error"));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Gets the client IP address from the request.
     * Handles cases where the request goes through proxies or load balancers.
     */
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }
    
    // POST /api/auth/logout - User logout
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@RequestBody LogoutRequest logoutRequest) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "POST /api/auth/logout - User logout attempt"));
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Request body: username=" + logoutRequest.getUsername()));
        
        try {
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Calling UserService.logout()"));
            userService.logout(logoutRequest.getUsername());
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Logout successful for user: " + logoutRequest.getUsername()));
            ApiResponse<String> response = ApiResponse.success(
                MessageConstants.LOGOUT_SUCCESSFUL, 
                "User logged out successfully", 
                HttpStatus.OK.value()
            );
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Sending 200 response for successful logout"));
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Logout failed: " + e.getMessage()));
            ApiResponse<String> response = ApiResponse.error(
                e.getMessage(), 
                HttpStatus.BAD_REQUEST.value()
            );
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Sending 400 response for failed logout"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Unexpected error during logout: " + e.getMessage()));
            ApiResponse<String> response = ApiResponse.error(
                MessageConstants.INTERNAL_SERVER_ERROR, 
                HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Sending 500 response for logout error"));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
