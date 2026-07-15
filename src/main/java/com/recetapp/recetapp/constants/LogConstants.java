package com.recetapp.recetapp.constants;

/**
 * Constantes para mensajes de log utilizados en todo el proyecto.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public final class LogConstants {
    
    // Constructor privado para evitar instanciación
    private LogConstants() {}
    
    // ========================================
    // PREFIJOS DE SERVICIOS
    // ========================================
    
    /** Prefijo para logs del RecipeService */
    public static final String RECIPE_SERVICE_PREFIX = "[RecipeService]";
    
    /** Prefijo para logs del UserService */
    public static final String USER_SERVICE_PREFIX = "[UserService]";
    
    /** Prefijo para logs del SessionService */
    public static final String SESSION_SERVICE_PREFIX = "[SessionService]";
    
    /** Prefijo para logs del FollowService */
    public static final String FOLLOW_SERVICE_PREFIX = "[FollowService]";
    
    /** Prefijo para logs del CacheService */
    public static final String CACHE_SERVICE_PREFIX = "[CacheService]";
    
    /** Prefijo para logs del RecipeImageService */
    public static final String RECIPE_IMAGE_SERVICE_PREFIX = "[RecipeImageService]";
    
    /** Prefijo para logs del FavoriteService */
    public static final String FAVORITE_SERVICE_PREFIX = "[FavoriteService]";
    
    /** Prefijo para logs del CommentService */
    public static final String COMMENT_SERVICE_PREFIX = "[CommentService]";
    
    /** Prefijo para logs de controladores */
    public static final String CONTROLLER_PREFIX = "[Controller]";
    
    // ========================================
    // MENSAJES DE RECETAS
    // ========================================
    
    /** Obteniendo recetas con información nutricional para usuario */
    public static final String GETTING_RECIPES_FOR_USER = "Obteniendo recetas con información nutricional para usuario: %s";
    
    /** Se encontraron X recetas para el usuario */
    public static final String FOUND_RECIPES_FOR_USER = "Se encontraron %d recetas para el usuario %s";
    
    /** Obteniendo receta con información nutricional */
    public static final String GETTING_RECIPE_BY_ID = "Obteniendo receta con información nutricional: %s";
    
    /** Receta encontrada */
    public static final String RECIPE_FOUND = "Receta encontrada: %s";
    
    /** Receta no encontrada */
    public static final String RECIPE_NOT_FOUND_LOG = "Receta no encontrada: %s";
    
    /** Buscando recetas por título */
    public static final String SEARCHING_RECIPES_BY_TITLE = "Buscando recetas por título: '%s' para usuario: %s";
    
    /** Se encontraron X recetas con título */
    public static final String FOUND_RECIPES_BY_TITLE = "Se encontraron %d recetas con título '%s'";
    
    /** Creando nueva receta */
    public static final String CREATING_NEW_RECIPE = "Creando nueva receta: %s";
    
    /** Receta creada exitosamente */
    public static final String RECIPE_CREATED_SUCCESSFULLY = "Receta creada exitosamente con ID: %s";
    
    /** Actualizando receta */
    public static final String UPDATING_RECIPE = "Actualizando receta: %s";
    
    /** Receta actualizada exitosamente */
    public static final String RECIPE_UPDATED_SUCCESSFULLY = "Receta actualizada exitosamente: %s";
    
    /** Eliminando receta */
    public static final String DELETING_RECIPE = "Eliminando receta: %s";
    
    /** Receta eliminada exitosamente */
    public static final String RECIPE_DELETED_SUCCESSFULLY = "Receta eliminada exitosamente: %s";
    
    // ========================================
    // MENSAJES DE INFORMACIÓN NUTRICIONAL
    // ========================================
    
    /** Guardando información nutricional para receta */
    public static final String SAVING_NUTRITIONAL_INFO = "Guardando información nutricional para receta: %s";
    
    /** Información nutricional guardada exitosamente */
    public static final String NUTRITIONAL_INFO_SAVED = "Información nutricional guardada exitosamente";
    
    /** Actualizando información nutricional para receta */
    public static final String UPDATING_NUTRITIONAL_INFO = "Actualizando información nutricional para receta: %s";
    
    /** Información nutricional actualizada exitosamente */
    public static final String NUTRITIONAL_INFO_UPDATED = "Información nutricional actualizada exitosamente";
    
    /** Información nutricional eliminada para receta */
    public static final String NUTRITIONAL_INFO_DELETED = "Información nutricional eliminada para receta: %s";
    
    // ========================================
    // MENSAJES DE IMÁGENES
    // ========================================
    
    /** Se eliminaron X imágenes de la receta */
    public static final String IMAGES_DELETED_FOR_RECIPE = "Se eliminaron %d imágenes de la receta: %s";
    
    /** Subiendo imagen para receta */
    public static final String UPLOADING_IMAGE_FOR_RECIPE = "Subiendo imagen para receta: %s";
    
    /** Imagen subida exitosamente */
    public static final String IMAGE_UPLOADED_SUCCESSFULLY = "Imagen subida exitosamente con ID: %s";
    
    /** Obteniendo imágenes para receta */
    public static final String GETTING_IMAGES_FOR_RECIPE = "Obteniendo imágenes para receta: %s";
    
    /** Eliminando imagen */
    public static final String DELETING_IMAGE = "Eliminando imagen: %s";
    
    /** Imagen eliminada exitosamente */
    public static final String IMAGE_DELETED_SUCCESSFULLY = "Imagen eliminada exitosamente: %s";
    
    // ========================================
    // MENSAJES DE USUARIOS
    // ========================================
    
    /** Usuario autenticado exitosamente */
    public static final String USER_AUTHENTICATED_SUCCESSFULLY = "Usuario autenticado exitosamente: %s";
    
    /** Usuario no autenticado */
    public static final String USER_NOT_AUTHENTICATED_LOG = "Usuario no autenticado: %s";
    
    /** Usuario creado exitosamente */
    public static final String USER_CREATED_SUCCESSFULLY = "Usuario creado exitosamente con ID: %s";
    
    /** Usuario actualizado exitosamente */
    public static final String USER_UPDATED_SUCCESSFULLY = "Usuario actualizado exitosamente: %s";
    
    /** Usuario eliminado exitosamente */
    public static final String USER_DELETED_SUCCESSFULLY = "Usuario eliminado exitosamente: %s";
    
    /** Sesión iniciada exitosamente */
    public static final String SESSION_STARTED_SUCCESSFULLY = "Sesión iniciada exitosamente para usuario: %s";
    
    /** Sesión cerrada exitosamente */
    public static final String SESSION_CLOSED_SUCCESSFULLY = "Sesión cerrada exitosamente para usuario: %s";
    
    // ========================================
    // MENSAJES DE ERROR
    // ========================================
    
    /** Error obteniendo recetas para usuario */
    public static final String ERROR_GETTING_RECIPES_FOR_USER = "Error obteniendo recetas para usuario %s: %s";
    
    /** Error obteniendo receta */
    public static final String ERROR_GETTING_RECIPE = "Error obteniendo receta %s: %s";
    
    /** Error buscando recetas por título */
    public static final String ERROR_SEARCHING_RECIPES_BY_TITLE = "Error buscando recetas por título '%s': %s";
    
    /** Error creando receta */
    public static final String ERROR_CREATING_RECIPE_LOG = "Error creando receta: %s";
    
    /** Error actualizando receta */
    public static final String ERROR_UPDATING_RECIPE_LOG = "Error actualizando receta %s: %s";
    
    /** Error eliminando receta */
    public static final String ERROR_DELETING_RECIPE_LOG = "Error eliminando receta %s: %s";
    
    /** Error guardando información nutricional */
    public static final String ERROR_SAVING_NUTRITIONAL_INFO_LOG = "Error guardando información nutricional: %s";
    
    /** Error actualizando información nutricional */
    public static final String ERROR_UPDATING_NUTRITIONAL_INFO_LOG = "Error actualizando información nutricional: %s";
    
    /** Error subiendo imagen */
    public static final String ERROR_UPLOADING_IMAGE = "Error subiendo imagen: %s";
    
    /** Error obteniendo imágenes */
    public static final String ERROR_GETTING_IMAGES = "Error obteniendo imágenes: %s";
    
    /** Error eliminando imagen */
    public static final String ERROR_DELETING_IMAGE_LOG = "Error eliminando imagen: %s";
    
    // ========================================
    // MENSAJES DE VALIDACIÓN
    // ========================================
    
    /** Validando datos de entrada */
    public static final String VALIDATING_INPUT_DATA = "Validando datos de entrada para %s";
    
    /** Datos validados exitosamente */
    public static final String DATA_VALIDATED_SUCCESSFULLY = "Datos validados exitosamente para %s";
    
    /** Error de validación */
    public static final String VALIDATION_ERROR = "Error de validación: %s";
    
    // ========================================
    // MENSAJES DE BASE DE DATOS
    // ========================================
    
    /** Ejecutando consulta en base de datos */
    public static final String EXECUTING_DATABASE_QUERY = "Ejecutando consulta en base de datos: %s";
    
    /** Consulta ejecutada exitosamente */
    public static final String QUERY_EXECUTED_SUCCESSFULLY = "Consulta ejecutada exitosamente";
    
    /** Error ejecutando consulta */
    public static final String ERROR_EXECUTING_QUERY = "Error ejecutando consulta: %s";
    
    /** Transacción iniciada */
    public static final String TRANSACTION_STARTED = "Transacción iniciada para %s";
    
    /** Transacción completada exitosamente */
    public static final String TRANSACTION_COMPLETED = "Transacción completada exitosamente para %s";
    
    /** Transacción fallida */
    public static final String TRANSACTION_FAILED = "Transacción fallida para %s: %s";
    
    // ========================================
    // MENSAJES DE CONTROLADORES
    // ========================================
    
    /** Endpoint llamado */
    public static final String ENDPOINT_CALLED = "%s %s - %s";
    
    /** Respuesta enviada exitosamente */
    public static final String RESPONSE_SENT_SUCCESSFULLY = "Respuesta enviada exitosamente para %s";
    
    /** Error procesando request */
    public static final String ERROR_PROCESSING_REQUEST = "Error procesando request: %s";
    
    // ========================================
    // MENSAJES DE CACHE
    // ========================================
    
    /** Cache hit */
    public static final String CACHE_HIT = "Cache hit para clave: %s";
    
    /** Cache miss */
    public static final String CACHE_MISS = "Cache miss para clave: %s";
    
    /** Cache actualizado */
    public static final String CACHE_UPDATED = "Cache actualizado para clave: %s";
    
    /** Cache limpiado */
    public static final String CACHE_CLEARED = "Cache limpiado";
    
    // ========================================
    // MENSAJES DE SEGURIDAD
    // ========================================
    
    /** Intento de acceso no autorizado */
    public static final String UNAUTHORIZED_ACCESS_ATTEMPT = "Intento de acceso no autorizado desde IP: %s";
    
    /** Usuario bloqueado */
    public static final String USER_BLOCKED = "Usuario bloqueado: %s";
    
    /** Múltiples intentos fallidos */
    public static final String MULTIPLE_FAILED_ATTEMPTS = "Múltiples intentos fallidos para usuario: %s";
    
    // ========================================
    // MENSAJES DE PERFORMANCE
    // ========================================
    
    /** Operación completada en X ms */
    public static final String OPERATION_COMPLETED_IN_TIME = "Operación %s completada en %d ms";
    
    /** Operación lenta detectada */
    public static final String SLOW_OPERATION_DETECTED = "Operación lenta detectada: %s tomó %d ms";
    
    /** Timeout de operación */
    public static final String OPERATION_TIMEOUT = "Timeout de operación: %s después de %d ms";
    
    // ========================================
    // MÉTODOS UTILITARIOS
    // ========================================
    
    /**
     * Formatea un mensaje de log con parámetros.
     * 
     * @param message Mensaje base
     * @param params Parámetros para formatear
     * @return Mensaje formateado
     */
    public static String format(String message, Object... params) {
        return String.format(message, params);
    }
    
    /**
     * Construye un mensaje de log con prefijo de servicio.
     * 
     * @param servicePrefix Prefijo del servicio
     * @param message Mensaje del log
     * @return Mensaje completo con prefijo
     */
    public static String buildLogMessage(String servicePrefix, String message) {
        return String.format("%s %s", servicePrefix, message);
    }
    
    /**
     * Construye un mensaje de log con prefijo y parámetros.
     * 
     * @param servicePrefix Prefijo del servicio
     * @param message Mensaje del log
     * @param params Parámetros para formatear
     * @return Mensaje completo formateado
     */
    public static String buildLogMessage(String servicePrefix, String message, Object... params) {
        return String.format("%s %s", servicePrefix, format(message, params));
    }
}
