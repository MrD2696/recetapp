package com.recetapp.recetapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recetapp.recetapp.constants.LogConstants;
import com.recetapp.recetapp.constants.MessageConstants;
import com.recetapp.recetapp.dto.ApiResponse;
import com.recetapp.recetapp.dto.FollowRequest;
import com.recetapp.recetapp.dto.UserProfileDTO;
import com.recetapp.recetapp.service.FollowService;

/**
 * Controlador para manejar las operaciones de followers entre usuarios.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@RestController
@RequestMapping("/api/follow")
@CrossOrigin(origins = "*")
public class FollowController {
    
    @Autowired
    private FollowService followService;
    
    /**
     * Hace que un usuario siga a otro usuario.
     * 
     * @param followerId ID del usuario que va a seguir
     * @param followRequest DTO con el ID del usuario a seguir
     * @return Respuesta con el resultado de la operación
     */
    @PostMapping("/{followerId}")
    public ResponseEntity<ApiResponse<String>> followUser(
            @PathVariable Integer followerId,
            @RequestBody FollowRequest followRequest) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "POST /api/follow/" + followerId + " - User " + followerId + " following user " + followRequest.getFollowedUserId()));
        
        try {
            String mensaje = followService.followUserSP(followerId, followRequest.getFollowedUserId());
            ApiResponse<String> response = ApiResponse.success(mensaje);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponse<String> response = ApiResponse.error(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Hace que un usuario deje de seguir a otro usuario.
     * 
     * @param followerId ID del usuario que va a dejar de seguir
     * @param followedId ID del usuario que va a dejar de ser seguido
     * @return Respuesta con el resultado de la operación
     */
    @DeleteMapping("/{followerId}/{followedId}")
    public ResponseEntity<ApiResponse<String>> unfollowUser(
            @PathVariable Integer followerId,
            @PathVariable Integer followedId) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "DELETE /api/follow/" + followerId + "/" + followedId + " - User " + followerId + " unfollowing user " + followedId));
        
        try {
            boolean wasFollowing = followService.unfollowUser(followerId, followedId);
            
            String message = wasFollowing ? 
                MessageConstants.USER_UNFOLLOWED_SUCCESSFULLY : 
                MessageConstants.USER_NOT_FOLLOWING;
            
            ApiResponse<String> response = ApiResponse.success(message, "Unfollow operation completed");
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "User " + followerId + " no longer follows user " + followedId));
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error unfollowing user: " + e.getMessage()));
            
            ApiResponse<String> response = ApiResponse.error(MessageConstants.ERROR_UNFOLLOWING_USER + ": " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Obtiene el perfil de un usuario con información de followers.
     * 
     * @param userId ID del usuario del perfil
     * @param currentUserId ID del usuario actual (opcional)
     * @return Perfil del usuario con información de followers
     */
    @GetMapping("/profile/{userId}")
    public ResponseEntity<ApiResponse<UserProfileDTO>> getUserProfile(
            @PathVariable Integer userId,
            @RequestParam(required = false) Integer currentUserId) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "GET /api/follow/profile/" + userId + " - Retrieving profile for user " + userId));
        
        try {
            UserProfileDTO profile = followService.getUserProfile(userId, currentUserId);
            
            ApiResponse<UserProfileDTO> response = ApiResponse.success(
                MessageConstants.PROFILE_RETRIEVED_SUCCESSFULLY, 
                profile
            );
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Profile retrieved for user " + userId + " with " + profile.getFollowersCount() + " followers"));
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error retrieving user profile " + userId + ": " + e.getMessage()));
            
            ApiResponse<UserProfileDTO> response = ApiResponse.error(
                MessageConstants.ERROR_RETRIEVING_PROFILE + ": " + e.getMessage()
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Obtiene la lista de usuarios que sigue un usuario específico.
     * 
     * @param userId ID del usuario
     * @return Lista de usuarios que sigue
     */
    @GetMapping("/{userId}/following")
    public ResponseEntity<ApiResponse<List<UserProfileDTO>>> getFollowingUsers(@PathVariable Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "GET /api/follow/" + userId + "/following - Retrieving users that user " + userId + " follows"));
        
        try {
            List<UserProfileDTO> followingUsers = followService.getFollowingUsers(userId);
            
            ApiResponse<List<UserProfileDTO>> response = ApiResponse.success(
                MessageConstants.FOLLOWING_USERS_RETRIEVED_SUCCESSFULLY, 
                followingUsers
            );
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "User " + userId + " follows " + followingUsers.size() + " users"));
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error retrieving following users for user " + userId + ": " + e.getMessage()));
            
            ApiResponse<List<UserProfileDTO>> response = ApiResponse.error(
                MessageConstants.ERROR_RETRIEVING_FOLLOWING + ": " + e.getMessage()
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Obtiene la lista de seguidores de un usuario específico.
     * 
     * @param userId ID del usuario
     * @return Lista de seguidores
     */
    @GetMapping("/{userId}/followers")
    public ResponseEntity<ApiResponse<List<UserProfileDTO>>> getFollowers(@PathVariable Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "GET /api/follow/" + userId + "/followers - Retrieving followers for user " + userId));
        
        try {
            List<UserProfileDTO> followers = followService.getFollowers(userId);
            
            ApiResponse<List<UserProfileDTO>> response = ApiResponse.success(
                MessageConstants.FOLLOWERS_RETRIEVED_SUCCESSFULLY, 
                followers
            );
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "User " + userId + " has " + followers.size() + " followers"));
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error retrieving followers for user " + userId + ": " + e.getMessage()));
            
            ApiResponse<List<UserProfileDTO>> response = ApiResponse.error(
                MessageConstants.ERROR_RETRIEVING_FOLLOWERS + ": " + e.getMessage()
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Verifica si un usuario sigue a otro.
     * 
     * @param followerId ID del usuario que podría estar siguiendo
     * @param followedId ID del usuario que podría ser seguido
     * @return true si lo sigue, false en caso contrario
     */
    @GetMapping("/check/{followerId}/{followedId}")
    public ResponseEntity<ApiResponse<Boolean>> isFollowing(
            @PathVariable Integer followerId,
            @PathVariable Integer followedId) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "GET /api/follow/check/" + followerId + "/" + followedId + " - Checking if user " + followerId + " follows user " + followedId));
        
        try {
            boolean isFollowing = followService.isFollowing(followerId, followedId);
            
            ApiResponse<Boolean> response = ApiResponse.success(
                MessageConstants.FOLLOW_CHECK_COMPLETED, 
                isFollowing
            );
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "User " + followerId + (isFollowing ? " DOES" : " DOES NOT") + " follow user " + followedId));
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error checking follow relationship: " + e.getMessage()));
            
            ApiResponse<Boolean> response = ApiResponse.error(
                MessageConstants.ERROR_CHECKING_FOLLOW + ": " + e.getMessage()
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
}
