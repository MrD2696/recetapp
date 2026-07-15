package com.recetapp.recetapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recetapp.recetapp.constants.ExceptionConstants;
import com.recetapp.recetapp.constants.LogConstants;
import com.recetapp.recetapp.dto.UserProfileDTO;
import com.recetapp.recetapp.exception.UserException;
import com.recetapp.recetapp.model.User;
import com.recetapp.recetapp.model.UserFollower;
import com.recetapp.recetapp.repository.UserFollowerRepository;
import com.recetapp.recetapp.repository.UserRepository;

/**
 * Servicio para manejar las operaciones de followers entre usuarios.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Service
public class FollowService {
    
    @Autowired
    private UserFollowerRepository userFollowerRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;

    /**
     * Hace que un usuario siga a otro usando procedimiento almacenado.
     * @param followerId ID del usuario que sigue
     * @param followedId ID del usuario a seguir
     * @return mensaje resultado
     */
    public String followUserSP(Integer followerId, Integer followedId) {
        String sql = "{call sp_FollowUser(?, ?)}";
        try {
            java.util.Map<String, Object> result = jdbcTemplate.queryForMap(sql, followerId, followedId);
            // El SP debe devolver un solo registro con campos Resultado y Mensaje
            String mensaje = "";
            if (result.containsKey("Mensaje")) {
                mensaje = String.valueOf(result.get("Mensaje"));
            } else if (result.containsKey("Resultado")) {
                mensaje = String.valueOf(result.get("Resultado"));
            }
            return mensaje;
        } catch (org.springframework.dao.DataAccessException e) {
            Throwable cause = e.getCause();
            if (cause != null && cause.getMessage() != null) {
                throw new RuntimeException(cause.getMessage());
            }
            throw new RuntimeException("Error al seguir usuario: " + e.getMessage());
        }
    }
    
    /**
     * Hace que un usuario siga a otro usuario.
     * 
     * @param followerId ID del usuario que va a seguir
     * @param followedId ID del usuario que va a ser seguido
     * @return true si se creó la relación, false si ya existía
     */
    @Transactional
    @CacheEvict(value = {"userProfile", "followersCount", "followingCount", "followingList", "followersList", "isFollowing"}, allEntries = true)
    public boolean followUser(Integer followerId, Integer followedId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.FOLLOW_SERVICE_PREFIX, 
            "User " + followerId + " attempting to follow user " + followedId));
        
        // Validar que no se siga a sí mismo
        if (followerId.equals(followedId)) {
            throw new UserException(ExceptionConstants.format(
                ExceptionConstants.USER_CANNOT_FOLLOW_SELF, followerId));
        }
        
        // Verificar que ambos usuarios existan
        User follower = userRepository.findById(followerId)
            .orElseThrow(() -> new UserException(ExceptionConstants.format(
                ExceptionConstants.USER_NOT_FOUND, followerId)));
        
        User followed = userRepository.findById(followedId)
            .orElseThrow(() -> new UserException(ExceptionConstants.format(
                ExceptionConstants.USER_NOT_FOUND, followedId)));
        
        // Verificar si ya existe la relación
        if (userFollowerRepository.existsByFollowerUserIdAndFollowedUserId(followerId, followedId)) {
                    System.out.println(LogConstants.buildLogMessage(LogConstants.FOLLOW_SERVICE_PREFIX, 
            "User " + followerId + " already follows user " + followedId));
            return false;
        }
        
        // Crear la relación de seguimiento
        UserFollower userFollower = new UserFollower(follower, followed);
        userFollowerRepository.save(userFollower);
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.FOLLOW_SERVICE_PREFIX, 
            "User " + followerId + " now follows user " + followedId));
        
        return true;
    }
    
    /**
     * Hace que un usuario deje de seguir a otro usuario.
     * 
     * @param followerId ID del usuario que va a dejar de seguir
     * @param followedId ID del usuario que va a dejar de ser seguido
     * @return true si se eliminó la relación, false si no existía
     */
    @Transactional
    @CacheEvict(value = {"userProfile", "followersCount", "followingCount", "followingList", "followersList", "isFollowing"}, allEntries = true)
    public boolean unfollowUser(Integer followerId, Integer followedId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.FOLLOW_SERVICE_PREFIX, 
            "User " + followerId + " attempting to unfollow user " + followedId));
        
        // Verificar si existe la relación
        if (!userFollowerRepository.existsByFollowerUserIdAndFollowedUserId(followerId, followedId)) {
                    System.out.println(LogConstants.buildLogMessage(LogConstants.FOLLOW_SERVICE_PREFIX, 
            "User " + followerId + " does not follow user " + followedId));
            return false;
        }
        
        // Eliminar la relación
        userFollowerRepository.deleteByFollowerUserIdAndFollowedUserId(followerId, followedId);
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.FOLLOW_SERVICE_PREFIX, 
            "User " + followerId + " no longer follows user " + followedId));
        
        return true;
    }
    
    /**
     * Obtiene el perfil de un usuario con información de followers.
     * 
     * @param userId ID del usuario del perfil
     * @param currentUserId ID del usuario actual (para verificar si lo sigue)
     * @return UserProfileDTO con información del perfil
     */
    @Cacheable(value = "userProfile", key = "#userId + '_' + #currentUserId")
    public UserProfileDTO getUserProfile(Integer userId, Integer currentUserId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.FOLLOW_SERVICE_PREFIX, 
            "Retrieving profile for user " + userId + " for current user " + currentUserId));
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserException(ExceptionConstants.format(
                ExceptionConstants.USER_NOT_FOUND, userId)));
        
        // Contar followers y following
        long followersCount = userFollowerRepository.countByFollowedUserId(userId);
        long followingCount = userFollowerRepository.countByFollowerUserId(userId);
        
        // Verificar si el usuario actual sigue a este usuario
        boolean isFollowing = false;
        if (currentUserId != null && !currentUserId.equals(userId)) {
            isFollowing = userFollowerRepository.existsByFollowerUserIdAndFollowedUserId(currentUserId, userId);
        }
        
        UserProfileDTO profile = new UserProfileDTO(
            user.getUserId(),
            user.getUsername(),
            user.getEmail(),
            user.getCreatedAt(),
            user.getLastLogin(),
            user.getOnline(),
            (int) followersCount,
            (int) followingCount
        );
        profile.setFollowing(isFollowing);
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.FOLLOW_SERVICE_PREFIX, 
            "Profile retrieved for user " + userId + " with " + followersCount + " followers"));
        
        return profile;
    }
    
    /**
     * Obtiene la lista de usuarios que sigue un usuario específico.
     * 
     * @param userId ID del usuario
     * @return Lista de UserProfileDTO de usuarios que sigue
     */
    @Cacheable(value = "followingList", key = "#userId")
    public List<UserProfileDTO> getFollowingUsers(Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.FOLLOW_SERVICE_PREFIX, 
            "Retrieving users that user " + userId + " follows"));
        
        List<UserFollower> following = userFollowerRepository.findByFollowerUserId(userId);
        
        List<UserProfileDTO> followingUsers = following.stream()
            .map(uf -> getUserProfile(uf.getFollowed().getUserId(), userId))
            .collect(Collectors.toList());
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.FOLLOW_SERVICE_PREFIX, 
            "User " + userId + " follows " + followingUsers.size() + " users"));
        
        return followingUsers;
    }
    
    /**
     * Obtiene la lista de seguidores de un usuario específico.
     * 
     * @param userId ID del usuario
     * @return Lista de UserProfileDTO de seguidores
     */
    @Cacheable(value = "followersList", key = "#userId")
    public List<UserProfileDTO> getFollowers(Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.FOLLOW_SERVICE_PREFIX, 
            "Retrieving followers for user " + userId));
        
        List<UserFollower> followers = userFollowerRepository.findByFollowedUserId(userId);
        
        List<UserProfileDTO> followersList = followers.stream()
            .map(uf -> getUserProfile(uf.getFollower().getUserId(), userId))
            .collect(Collectors.toList());
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.FOLLOW_SERVICE_PREFIX, 
            "User " + userId + " has " + followersList.size() + " followers"));
        
        return followersList;
    }
    
    /**
     * Verifica si un usuario sigue a otro.
     * 
     * @param followerId ID del usuario que podría estar siguiendo
     * @param followedId ID del usuario que podría ser seguido
     * @return true si lo sigue, false en caso contrario
     */
    @Cacheable(value = "isFollowing", key = "#followerId + '_' + #followedId")
    public boolean isFollowing(Integer followerId, Integer followedId) {
        return userFollowerRepository.existsByFollowerUserIdAndFollowedUserId(followerId, followedId);
    }
}
