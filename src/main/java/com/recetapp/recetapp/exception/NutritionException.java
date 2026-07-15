package com.recetapp.recetapp.exception;

/**
 * Excepción personalizada para operaciones relacionadas con información nutricional.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public class NutritionException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    private final String errorCode;
    
    /**
     * Constructor con mensaje.
     * 
     * @param message Mensaje de error
     */
    public NutritionException(String message) {
        super(message);
        this.errorCode = "NUTRITION_ERROR";
    }
    
    /**
     * Constructor con mensaje y causa.
     * 
     * @param message Mensaje de error
     * @param cause Causa del error
     */
    public NutritionException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "NUTRITION_ERROR";
    }
    
    /**
     * Constructor con mensaje y código de error.
     * 
     * @param message Mensaje de error
     * @param errorCode Código de error
     */
    public NutritionException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    /**
     * Constructor con mensaje, código de error y causa.
     * 
     * @param message Mensaje de error
     * @param errorCode Código de error
     * @param cause Causa del error
     */
    public NutritionException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    /**
     * Obtiene el código de error.
     * 
     * @return Código de error
     */
    public String getErrorCode() {
        return errorCode;
    }
}
