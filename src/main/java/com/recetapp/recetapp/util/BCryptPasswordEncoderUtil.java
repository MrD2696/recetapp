package com.recetapp.recetapp.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utility class for password encoding and verification using BCrypt.
 * BCrypt is a one-way hashing algorithm that is very secure and resistant
 * to brute force attacks, rainbow tables, and other common attack vectors.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public class BCryptPasswordEncoderUtil {
    
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); // Strength 12 (default is 10)
    
    /**
     * Encodes a plain text password using BCrypt.
     * This is a one-way operation - the password cannot be decoded back.
     * 
     * @param plainPassword the plain text password to encode
     * @return the BCrypt hashed password
     */
    public static String encodePassword(String plainPassword) {
        if (plainPassword == null || plainPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        
        try {
            return encoder.encode(plainPassword);
        } catch (Exception e) {
            throw new RuntimeException("Error encoding password with BCrypt: " + e.getMessage());
        }
    }
    
    /**
     * Verifies if a plain text password matches a BCrypt hashed password.
     * This is the correct way to verify passwords with BCrypt.
     * 
     * @param plainPassword the plain text password to verify
     * @param hashedPassword the BCrypt hashed password to compare against
     * @return true if passwords match, false otherwise
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        
        try {
            return encoder.matches(plainPassword, hashedPassword);
        } catch (Exception e) {
            System.err.println("Error verifying password with BCrypt: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Gets the BCrypt encoder instance for advanced usage.
     * 
     * @return the BCryptPasswordEncoder instance
     */
    public static BCryptPasswordEncoder getEncoder() {
        return encoder;
    }
}
