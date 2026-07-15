package com.recetapp.recetapp.constants;

/**
 * Constantes para mensajes de excepciones utilizados en todo el proyecto.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public final class ExceptionConstants {
    
    // Constructor privado para evitar instanciación
    private ExceptionConstants() {}
    
    // ========================================
    // EXCEPCIONES GENERALES
    // ========================================
    
    /** Mensaje genérico para errores internos del servidor */
    public static final String INTERNAL_SERVER_ERROR = "Error interno del servidor";
    
    /** Mensaje genérico para operaciones no autorizadas */
    public static final String UNAUTHORIZED_OPERATION = "Operación no autorizada";
    
    /** Mensaje genérico para recursos no encontrados */
    public static final String RESOURCE_NOT_FOUND = "Recurso no encontrado";
    
    /** Mensaje genérico para validación fallida */
    public static final String VALIDATION_FAILED = "Validación fallida";
    
    // ========================================
    // EXCEPCIONES DE USUARIOS
    // ========================================
    
    /** Usuario no encontrado */
    public static final String USER_NOT_FOUND = "Usuario no encontrado con ID: %s";
    
    /** Usuario ya existe */
    public static final String USER_ALREADY_EXISTS = "El usuario ya existe con el email: %s";
    
    /** Credenciales inválidas */
    public static final String INVALID_CREDENTIALS = "Credenciales inválidas";
    
    /** Sesión no válida */
    public static final String INVALID_SESSION = "Sesión no válida o expirada";
    
    /** Usuario no autenticado */
    public static final String USER_NOT_AUTHENTICATED = "Usuario no autenticado";
    
    /** Usuario no puede seguirse a sí mismo */
    public static final String USER_CANNOT_FOLLOW_SELF = "El usuario %s no puede seguirse a sí mismo";
    
    // ========================================
    // EXCEPCIONES DE RECETAS
    // ========================================
    
    /** Receta no encontrada */
    public static final String RECIPE_NOT_FOUND = "Receta no encontrada con ID: %s";
    
    /** Error obteniendo recetas */
    public static final String ERROR_GETTING_RECIPES = "Error obteniendo recetas: %s";
    
    /** Error creando receta */
    public static final String ERROR_CREATING_RECIPE = "Error creando receta: %s";
    
    /** Error actualizando receta */
    public static final String ERROR_UPDATING_RECIPE = "Error actualizando receta: %s";
    
    /** Error eliminando receta */
    public static final String ERROR_DELETING_RECIPE = "Error eliminando receta: %s";
    
    /** Error buscando recetas */
    public static final String ERROR_SEARCHING_RECIPES = "Error buscando recetas: %s";
    
    // ========================================
    // EXCEPCIONES DE INFORMACIÓN NUTRICIONAL
    // ========================================
    
    /** Información nutricional no encontrada */
    public static final String NUTRITIONAL_INFO_NOT_FOUND = "Información nutricional no encontrada para la receta: %s";
    
    /** Error guardando información nutricional */
    public static final String ERROR_SAVING_NUTRITIONAL_INFO = "Error guardando información nutricional: %s";
    
    /** Error actualizando información nutricional */
    public static final String ERROR_UPDATING_NUTRITIONAL_INFO = "Error actualizando información nutricional: %s";
    
    // ========================================
    // EXCEPCIONES DE IMÁGENES DE RECETAS
    // ========================================
    
    /** Imagen no encontrada */
    public static final String IMAGE_NOT_FOUND = "Imagen no encontrada con ID: %s";
    
    /** Error subiendo imagen */
    public static final String ERROR_UPLOADING_IMAGE = "Error subiendo imagen: %s";
    
    /** Error obteniendo imágenes */
    public static final String ERROR_GETTING_IMAGES = "Error obteniendo imágenes: %s";
    
    /** Error eliminando imagen */
    public static final String ERROR_DELETING_IMAGE = "Error eliminando imagen: %s";
    
    /** Sin permisos para eliminar imagen */
    public static final String NO_PERMISSION_TO_DELETE_IMAGE = "No tienes permisos para eliminar esta imagen";
    
    // ========================================
    // EXCEPCIONES DE VALIDACIÓN
    // ========================================
    
    /** Campo requerido */
    public static final String FIELD_REQUIRED = "El campo '%s' es requerido";
    
    /** Campo inválido */
    public static final String FIELD_INVALID = "El campo '%s' tiene un valor inválido";
    
    /** Longitud mínima no cumplida */
    public static final String MIN_LENGTH_NOT_MET = "El campo '%s' debe tener al menos %d caracteres";
    
    /** Longitud máxima excedida */
    public static final String MAX_LENGTH_EXCEEDED = "El campo '%s' no puede exceder %d caracteres";
    
    /** Valor fuera de rango */
    public static final String VALUE_OUT_OF_RANGE = "El valor del campo '%s' debe estar entre %s y %s";
    
    // ========================================
    // EXCEPCIONES DE BASE DE DATOS
    // ========================================
    
    /** Error de conexión a base de datos */
    public static final String DATABASE_CONNECTION_ERROR = "Error de conexión a la base de datos";
    
    /** Error ejecutando consulta */
    public static final String QUERY_EXECUTION_ERROR = "Error ejecutando consulta en la base de datos";
    
    /** Error de transacción */
    public static final String TRANSACTION_ERROR = "Error en la transacción de base de datos";
    
    /** Error de constraint */
    public static final String CONSTRAINT_VIOLATION = "Violación de constraint de base de datos";
    
    // ========================================
    // EXCEPCIONES DE ARCHIVOS
    // ========================================
    
    /** Error leyendo archivo */
    public static final String ERROR_READING_FILE = "Error leyendo archivo: %s";
    
    /** Error escribiendo archivo */
    public static final String ERROR_WRITING_FILE = "Error escribiendo archivo: %s";
    
    /** Archivo no encontrado */
    public static final String FILE_NOT_FOUND = "Archivo no encontrado: %s";
    
    /** Tipo de archivo no soportado */
    public static final String UNSUPPORTED_FILE_TYPE = "Tipo de archivo no soportado: %s";
    
    /** Tamaño de archivo excedido */
    public static final String FILE_SIZE_EXCEEDED = "El tamaño del archivo excede el límite permitido";
    
    // ========================================
    // EXCEPCIONES DE SERVICIOS EXTERNOS
    // ========================================
    
    /** Error de servicio externo */
    public static final String EXTERNAL_SERVICE_ERROR = "Error en servicio externo: %s";
    
    /** Servicio externo no disponible */
    public static final String EXTERNAL_SERVICE_UNAVAILABLE = "Servicio externo no disponible";
    
    /** Timeout de servicio externo */
    public static final String EXTERNAL_SERVICE_TIMEOUT = "Timeout en servicio externo";
    
    // ========================================
    // EXCEPCIONES DE AUTENTICACIÓN Y AUTORIZACIÓN
    // ========================================
    
    /** Token inválido */
    public static final String INVALID_TOKEN = "Token de autenticación inválido";
    
    /** Token expirado */
    public static final String TOKEN_EXPIRED = "Token de autenticación expirado";
    
    /** Permisos insuficientes */
    public static final String INSUFFICIENT_PERMISSIONS = "Permisos insuficientes para realizar esta operación";
    
    /** Cuenta bloqueada */
    public static final String ACCOUNT_LOCKED = "La cuenta ha sido bloqueada";
    
    /** Demasiados intentos fallidos */
    public static final String TOO_MANY_FAILED_ATTEMPTS = "Demasiados intentos fallidos de inicio de sesión";
    
    // ========================================
    // EXCEPCIONES DE RATE LIMITING
    // ========================================
    
    /** Límite de requests excedido */
    public static final String RATE_LIMIT_EXCEEDED = "Límite de requests excedido. Intenta nuevamente en %s segundos";
    
    /** Demasiadas solicitudes */
    public static final String TOO_MANY_REQUESTS = "Demasiadas solicitudes. Por favor, espera antes de continuar";
    
    // ========================================
    // EXCEPCIONES DE VALIDACIÓN DE DATOS
    // ========================================
    
    /** Formato de email inválido */
    public static final String INVALID_EMAIL_FORMAT = "Formato de email inválido";
    
    /** Formato de fecha inválido */
    public static final String INVALID_DATE_FORMAT = "Formato de fecha inválido. Use: %s";
    
    /** Formato de número inválido */
    public static final String INVALID_NUMBER_FORMAT = "Formato de número inválido para el campo '%s'";
    
    /** URL inválida */
    public static final String INVALID_URL = "URL inválida: %s";
    
    // ========================================
    // EXCEPCIONES DE OPERACIONES DE NEGOCIO
    // ========================================
    
    /** Operación no permitida */
    public static final String OPERATION_NOT_ALLOWED = "Operación no permitida en el estado actual";
    
    /** Recurso en uso */
    public static final String RESOURCE_IN_USE = "El recurso está siendo utilizado y no puede ser eliminado";
    
    /** Dependencia no satisfecha */
    public static final String DEPENDENCY_NOT_SATISFIED = "No se puede realizar la operación debido a dependencias no satisfechas";
    
    /** Límite de recursos excedido */
    public static final String RESOURCE_LIMIT_EXCEEDED = "Límite de recursos excedido";
    
    // ========================================
    // MÉTODOS UTILITARIOS
    // ========================================
    
    /**
     * Formatea un mensaje con parámetros.
     * 
     * @param message Mensaje base
     * @param params Parámetros para formatear
     * @return Mensaje formateado
     */
    public static String format(String message, Object... params) {
        return String.format(message, params);
    }
    
    /**
     * Construye un mensaje de error con contexto.
     * 
     * @param operation Operación que falló
     * @param details Detalles del error
     * @return Mensaje completo
     */
    public static String buildErrorMessage(String operation, String details) {
        return String.format("Error en %s: %s", operation, details);
    }
}
