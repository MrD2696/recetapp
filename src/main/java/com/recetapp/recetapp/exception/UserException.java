package com.recetapp.recetapp.exception;

/**
 * Excepción personalizada para errores relacionados con usuarios.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public class UserException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    private final String errorCode;
    
    public UserException(String message) {
        super(message);
        this.errorCode = "USER_ERROR";
    }
    
    public UserException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "USER_ERROR";
    }
    
    public UserException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public UserException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}
