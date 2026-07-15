package com.recetapp.recetapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recetapp.recetapp.constants.LogConstants;
import com.recetapp.recetapp.dto.ApiResponse;
import com.recetapp.recetapp.dto.FavoriteDTO;
import com.recetapp.recetapp.service.FavoriteService;

/**
 * Controlador para manejar operaciones relacionadas con los favoritos de los usuarios.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "*")
public class FavoriteController {
    
    @Autowired
    private FavoriteService favoriteService;
    
    /**
     * Marca una receta como favorita para un usuario.
     * 
     * @param recipeId ID de la receta
     * @param userId ID del usuario
     * @return Respuesta con el favorito creado
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<FavoriteDTO>> addToFavorites(
            @RequestParam Integer recipeId,
            @RequestParam Integer userId) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "POST /api/favorites/add - Adding recipe " + recipeId + " to favorites for user " + userId));
        
        try {
            FavoriteDTO favorite = favoriteService.addToFavorites(recipeId, userId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Recipe " + recipeId + " added to favorites successfully for user " + userId));
            
            return ResponseEntity.ok(ApiResponse.success(
                "Recipe added to favorites successfully", 
                favorite
            ));
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error adding recipe to favorites: " + e.getMessage()));
            
            return ResponseEntity.badRequest().body(ApiResponse.error(
                "Error adding recipe to favorites: " + e.getMessage()
            ));
        }
    }
    
    /**
     * Remueve una receta de los favoritos de un usuario.
     * 
     * @param recipeId ID de la receta
     * @param userId ID del usuario
     * @return Respuesta de confirmación
     */
    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse<String>> removeFromFavorites(
            @RequestParam Integer recipeId,
            @RequestParam Integer userId) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "DELETE /api/favorites/remove - Removing recipe " + recipeId + " from favorites for user " + userId));
        
        try {
            favoriteService.removeFromFavorites(recipeId, userId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Recipe " + recipeId + " removed from favorites successfully for user " + userId));
            
            return ResponseEntity.ok(ApiResponse.success(
                "Recipe removed from favorites successfully", 
                "Recipe removed from favorites"
            ));
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error removing recipe from favorites: " + e.getMessage()));
            
            return ResponseEntity.badRequest().body(ApiResponse.error(
                "Error removing recipe from favorites: " + e.getMessage()
            ));
        }
    }
    
    /**
     * Verifica si una receta está en los favoritos de un usuario.
     * 
     * @param recipeId ID de la receta
     * @param userId ID del usuario
     * @return Respuesta con el estado del favorito
     */
    @GetMapping("/check")
    public ResponseEntity<ApiResponse<Boolean>> checkFavorite(
            @RequestParam Integer recipeId,
            @RequestParam Integer userId) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "GET /api/favorites/check - Checking if recipe " + recipeId + " is favorite for user " + userId));
        
        try {
            boolean isFavorite = favoriteService.isFavorite(recipeId, userId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Recipe " + recipeId + " favorite status for user " + userId + ": " + isFavorite));
            
            return ResponseEntity.ok(ApiResponse.success(
                "Favorite status checked successfully", 
                isFavorite
            ));
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error checking favorite status: " + e.getMessage()));
            
            return ResponseEntity.badRequest().body(ApiResponse.error(
                "Error checking favorite status: " + e.getMessage()
            ));
        }
    }
    
    /**
     * Obtiene todos los favoritos de un usuario.
     * 
     * @param userId ID del usuario
     * @return Respuesta con la lista de favoritos
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<FavoriteDTO>>> getUserFavorites(@PathVariable Integer userId) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "GET /api/favorites/user/" + userId + " - Getting favorites for user"));
        
        try {
            List<FavoriteDTO> favorites = favoriteService.getUserFavorites(userId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Found " + favorites.size() + " favorites for user " + userId));
            
            return ResponseEntity.ok(ApiResponse.success(
                "User favorites retrieved successfully", 
                favorites
            ));
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error getting user favorites: " + e.getMessage()));
            
            return ResponseEntity.badRequest().body(ApiResponse.error(
                "Error getting user favorites: " + e.getMessage()
            ));
        }
    }
    
    /**
     * Obtiene todos los favoritos de un usuario con información de receta.
     * 
     * @param userId ID del usuario
     * @return Respuesta con la lista de favoritos con información de receta
     */
    @GetMapping("/user/{userId}/with-recipe-info")
    public ResponseEntity<ApiResponse<List<FavoriteDTO>>> getUserFavoritesWithRecipeInfo(@PathVariable Integer userId) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "GET /api/favorites/user/" + userId + "/with-recipe-info - Getting favorites with recipe info for user"));
        
        try {
            List<FavoriteDTO> favorites = favoriteService.getUserFavoritesWithRecipeInfo(userId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Found " + favorites.size() + " favorites with recipe info for user " + userId));
            
            return ResponseEntity.ok(ApiResponse.success(
                "User favorites with recipe info retrieved successfully", 
                favorites
            ));
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error getting user favorites with recipe info: " + e.getMessage()));
            
            return ResponseEntity.badRequest().body(ApiResponse.error(
                "Error getting user favorites with recipe info: " + e.getMessage()
            ));
        }
    }
    
    /**
     * Cuenta cuántos favoritos tiene un usuario.
     * 
     * @param userId ID del usuario
     * @return Respuesta con el conteo de favoritos
     */
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<ApiResponse<Long>> getUserFavoritesCount(@PathVariable Integer userId) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "GET /api/favorites/user/" + userId + "/count - Getting favorites count for user"));
        
        try {
            long count = favoriteService.getUserFavoritesCount(userId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "User " + userId + " has " + count + " favorites"));
            
            return ResponseEntity.ok(ApiResponse.success(
                "User favorites count retrieved successfully", 
                count
            ));
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error getting user favorites count: " + e.getMessage()));
            
            return ResponseEntity.badRequest().body(ApiResponse.error(
                "Error getting user favorites count: " + e.getMessage()
            ));
        }
    }
    
    /**
     * Cuenta cuántos usuarios han marcado una receta como favorita.
     * 
     * @param recipeId ID de la receta
     * @return Respuesta con el conteo de usuarios que marcaron la receta como favorita
     */
    @GetMapping("/recipe/{recipeId}/count")
    public ResponseEntity<ApiResponse<Long>> getRecipeFavoritesCount(@PathVariable Integer recipeId) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "GET /api/favorites/recipe/" + recipeId + "/count - Getting favorites count for recipe"));
        
        try {
            long count = favoriteService.getRecipeFavoritesCount(recipeId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Recipe " + recipeId + " has " + count + " favorites"));
            
            return ResponseEntity.ok(ApiResponse.success(
                "Recipe favorites count retrieved successfully", 
                count
            ));
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error getting recipe favorites count: " + e.getMessage()));
            
            return ResponseEntity.badRequest().body(ApiResponse.error(
                "Error getting recipe favorites count: " + e.getMessage()
            ));
        }
    }
}
