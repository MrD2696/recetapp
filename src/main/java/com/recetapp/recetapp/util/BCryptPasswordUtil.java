package com.recetapp.recetapp.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utility class for password encoding and verification using BCrypt.
 * BCrypt is a one-way hashing algorithm that is very secure and resistant
 * to brute force attacks and rainbow table attacks.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public class BCryptPasswordUtil {
    
    private static final BCryptPasswordEncoder encoder;
    
    static {
        // BCrypt strength 12 is a good balance between security and performance
        // Higher values = more secure but slower
        encoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder(12);
    }
    
    /**
     * Encodes a plain text password using BCrypt.
     * Each call generates a different hash due to random salt.
     * 
     * @param plainPassword the plain text password to encode
     * @return the BCrypt encoded password hash
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
     * Verifies if a plain text password matches a BCrypt encoded password.
     * This is the correct way to verify BCrypt passwords.
     * 
     * @param plainPassword the plain text password to verify
     * @param encodedPassword the BCrypt encoded password hash to compare against
     * @return true if passwords match, false otherwise
     */
    public static boolean verifyPassword(String plainPassword, String encodedPassword) {
        if (plainPassword == null || encodedPassword == null) {
            return false;
        }
        
        try {
            return encoder.matches(plainPassword, encodedPassword);
        } catch (Exception e) {
            System.err.println("Error verifying password with BCrypt: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Checks if a string is a valid BCrypt hash.
     * 
     * @param encodedPassword the string to check
     * @return true if it's a valid BCrypt hash, false otherwise
     */
    public static boolean isValidBCryptHash(String encodedPassword) {
        if (encodedPassword == null || encodedPassword.trim().isEmpty()) {
            return false;
        }
        
        // BCrypt hashes start with $2a$, $2b$, or $2y$ and are 60 characters long
        return encodedPassword.matches("^\\$2[aby]\\$\\d{1,2}\\$[./A-Za-z0-9]{53}$");
    }
}
