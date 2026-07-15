package com.recetapp.recetapp.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de cache para optimizar el rendimiento de la aplicación.
 * Utiliza Spring Cache con cache en memoria por defecto.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Configuration
@EnableCaching
public class CacheConfig {
    
    // Spring Boot configurará automáticamente un cache manager en memoria
    // Si quieres usar Redis más adelante, solo necesitas agregar la dependencia
    // y configurar las propiedades en application.properties
}
