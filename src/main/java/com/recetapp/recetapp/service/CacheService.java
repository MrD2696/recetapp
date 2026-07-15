package com.recetapp.recetapp.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.recetapp.recetapp.constants.LogConstants;
import com.recetapp.recetapp.dto.UserProfileDTO;
import com.recetapp.recetapp.model.User;
import com.recetapp.recetapp.repository.UserFollowerRepository;
import com.recetapp.recetapp.repository.UserRepository;

/**
 * Servicio especializado para manejar el cache de followers.
 * Optimiza el rendimiento evitando consultas repetidas a la base de datos.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Service
public class CacheService {
    
    @Autowired
    private UserFollowerRepository userFollowerRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Obtiene el conteo de followers desde cache o base de datos.
     * 
     * @param userId ID del usuario
     * @return Número de followers
     */
    @Cacheable(value = "followersCount", key = "#userId")
    public int getFollowersCount(Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CACHE_SERVICE_PREFIX, 
            "Cache miss para followers count del usuario " + userId + ", consultando base de datos"));
        
        return (int) userFollowerRepository.countByFollowedUserId(userId);
    }
    
    /**
     * Obtiene el conteo de following desde cache o base de datos.
     * 
     * @param userId ID del usuario
     * @return Número de usuarios que sigue
     */
    @Cacheable(value = "followingCount", key = "#userId")
    public int getFollowingCount(Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CACHE_SERVICE_PREFIX, 
            "Cache miss para following count del usuario " + userId + ", consultando base de datos"));
        
        return (int) userFollowerRepository.countByFollowerUserId(userId);
    }
    
    /**
     * Obtiene la lista de IDs de followers desde cache o base de datos.
     * 
     * @param userId ID del usuario
     * @return Lista de IDs de followers
     */
    @Cacheable(value = "followersList", key = "#userId")
    public List<Integer> getFollowersIds(Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CACHE_SERVICE_PREFIX, 
            "Cache miss para followers IDs del usuario " + userId + ", consultando base de datos"));
        
        return userFollowerRepository.findFollowerUserIds(userId);
    }
    
    /**
     * Obtiene la lista de IDs de following desde cache o base de datos.
     * 
     * @param userId ID del usuario
     * @return Lista de IDs de usuarios que sigue
     */
    @Cacheable(value = "followingList", key = "#userId")
    public List<Integer> getFollowingIds(Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CACHE_SERVICE_PREFIX, 
            "Cache miss para following IDs del usuario " + userId + ", consultando base de datos"));
        
        return userFollowerRepository.findFollowingUserIds(userId);
    }
    
    /**
     * Verifica si un usuario sigue a otro desde cache o base de datos.
     * 
     * @param followerId ID del usuario que podría estar siguiendo
     * @param followedId ID del usuario que podría ser seguido
     * @return true si lo sigue, false en caso contrario
     */
    @Cacheable(value = "isFollowing", key = "#followerId + '_' + #followedId")
    public boolean isFollowing(Integer followerId, Integer followedId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CACHE_SERVICE_PREFIX, 
            "Cache miss para relación following " + followerId + " -> " + followedId + ", consultando base de datos"));
        
        return userFollowerRepository.existsByFollowerUserIdAndFollowedUserId(followerId, followedId);
    }
    
    /**
     * Limpia todo el cache relacionado con followers.
     * Se llama cuando hay cambios en las relaciones de seguimiento.
     */
    @CacheEvict(value = {"followersCount", "followingCount", "followersList", "followingList", "isFollowing", "userProfile"}, allEntries = true)
    public void clearFollowersCache() {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CACHE_SERVICE_PREFIX, 
            "Cache de followers limpiado completamente"));
    }
    
    /**
     * Limpia el cache específico de un usuario.
     * 
     * @param userId ID del usuario
     */
    @CacheEvict(value = {"followersCount", "followingCount", "followersList", "followingList", "userProfile"}, key = "#userId")
    public void clearUserCache(Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CACHE_SERVICE_PREFIX, 
            "Cache del usuario " + userId + " limpiado"));
    }
    
    /**
     * Limpia el cache de una relación específica.
     * 
     * @param followerId ID del usuario que sigue
     * @param followedId ID del usuario que es seguido
     */
    @CacheEvict(value = "isFollowing", key = "#followerId + '_' + #followedId")
    public void clearRelationshipCache(Integer followerId, Integer followedId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CACHE_SERVICE_PREFIX, 
            "Cache de relación " + followerId + " -> " + followedId + " limpiado"));
    }
}
