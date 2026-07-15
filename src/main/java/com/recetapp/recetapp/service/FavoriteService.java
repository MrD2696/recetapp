package com.recetapp.recetapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recetapp.recetapp.constants.LogConstants;
import com.recetapp.recetapp.constants.MessageConstants;
import com.recetapp.recetapp.dto.FavoriteDTO;
import com.recetapp.recetapp.model.Favorite;
import com.recetapp.recetapp.model.Recipe;
import com.recetapp.recetapp.repository.FavoriteRepository;
import com.recetapp.recetapp.repository.RecipeRepository;
import com.recetapp.recetapp.repository.UserRepository;

/**
 * Servicio para manejar operaciones relacionadas con los favoritos de los usuarios.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Service
public class FavoriteService {
    
    @Autowired
    private FavoriteRepository favoriteRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RecipeRepository recipeRepository;
    
    /**
     * Marca una receta como favorita para un usuario.
     * 
     * @param recipeId ID de la receta
     * @param userId ID del usuario
     * @return DTO del favorito creado
     */
    public FavoriteDTO addToFavorites(Integer recipeId, Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.FAVORITE_SERVICE_PREFIX, 
            "Adding recipe " + recipeId + " to favorites for user " + userId));
        
        try {
            // Verificar que el usuario existe
            if (!userRepository.existsById(userId)) {
                throw new RuntimeException(MessageConstants.USER_NOT_FOUND + userId);
            }
            
            // Verificar que la receta existe
            if (!recipeRepository.existsById(recipeId)) {
                throw new RuntimeException("Recipe not found with ID: " + recipeId);
            }
            
            // Verificar que no esté ya en favoritos
            if (favoriteRepository.existsByRecipeIdAndUserId(recipeId, userId)) {
                throw new RuntimeException("Recipe is already in favorites for this user");
            }
            
            // Crear y guardar el favorito
            Favorite favorite = new Favorite(recipeId, userId);
            Favorite savedFavorite = favoriteRepository.save(favorite);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.FAVORITE_SERVICE_PREFIX, 
                "Recipe " + recipeId + " added to favorites successfully for user " + userId));
            
            return convertToDTO(savedFavorite);
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.FAVORITE_SERVICE_PREFIX, 
                "Error adding recipe to favorites: " + e.getMessage()));
            throw e;
        }
    }
    
    /**
     * Remueve una receta de los favoritos de un usuario.
     * 
     * @param recipeId ID de la receta
     * @param userId ID del usuario
     */
    public void removeFromFavorites(Integer recipeId, Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.FAVORITE_SERVICE_PREFIX, 
            "Removing recipe " + recipeId + " from favorites for user " + userId));
        
        try {
            // Verificar que el favorito existe
            if (!favoriteRepository.existsByRecipeIdAndUserId(recipeId, userId)) {
                throw new RuntimeException("Recipe is not in favorites for this user");
            }
            
            // Eliminar el favorito
            favoriteRepository.deleteByRecipeIdAndUserId(recipeId, userId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.FAVORITE_SERVICE_PREFIX, 
                "Recipe " + recipeId + " removed from favorites successfully for user " + userId));
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.FAVORITE_SERVICE_PREFIX, 
                "Error removing recipe from favorites: " + e.getMessage()));
            throw e;
        }
    }
    
    /**
     * Verifica si una receta está en los favoritos de un usuario.
     * 
     * @param recipeId ID de la receta
     * @param userId ID del usuario
     * @return true si está en favoritos, false en caso contrario
     */
    public boolean isFavorite(Integer recipeId, Integer userId) {
        return favoriteRepository.existsByRecipeIdAndUserId(recipeId, userId);
    }
    
    /**
     * Obtiene todos los favoritos de un usuario.
     * 
     * @param userId ID del usuario
     * @return Lista de favoritos del usuario
     */
    public List<FavoriteDTO> getUserFavorites(Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.FAVORITE_SERVICE_PREFIX, 
            "Getting favorites for user " + userId));
        
        try {
            List<Favorite> favorites = favoriteRepository.findByUserIdOrderByCreatedAtDesc(userId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.FAVORITE_SERVICE_PREFIX, 
                "Found " + favorites.size() + " favorites for user " + userId));
            
            return favorites.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
                    
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.FAVORITE_SERVICE_PREFIX, 
                "Error getting user favorites: " + e.getMessage()));
            throw e;
        }
    }
    
    /**
     * Obtiene todos los favoritos de un usuario con información de receta.
     * 
     * @param userId ID del usuario
     * @return Lista de favoritos con información de receta
     */
    public List<FavoriteDTO> getUserFavoritesWithRecipeInfo(Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.FAVORITE_SERVICE_PREFIX, 
            "Getting favorites with recipe info for user " + userId));
        
        try {
            List<Favorite> favorites = favoriteRepository.findByUserIdWithRecipe(userId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.FAVORITE_SERVICE_PREFIX, 
                "Found " + favorites.size() + " favorites with recipe info for user " + userId));
            
            return favorites.stream()
                    .map(this::convertToDTOWithRecipeInfo)
                    .collect(Collectors.toList());
                    
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.FAVORITE_SERVICE_PREFIX, 
                "Error getting user favorites with recipe info: " + e.getMessage()));
            throw e;
        }
    }
    
    /**
     * Cuenta cuántos favoritos tiene un usuario.
     * 
     * @param userId ID del usuario
     * @return Número de favoritos
     */
    public long getUserFavoritesCount(Integer userId) {
        return favoriteRepository.countByUserId(userId);
    }
    
    /**
     * Cuenta cuántos usuarios han marcado una receta como favorita.
     * 
     * @param recipeId ID de la receta
     * @return Número de usuarios que han marcado la receta como favorita
     */
    public long getRecipeFavoritesCount(Integer recipeId) {
        return favoriteRepository.countByRecipeId(recipeId);
    }
    
    /**
     * Convierte una entidad Favorite a FavoriteDTO.
     * 
     * @param favorite Entidad Favorite
     * @return FavoriteDTO
     */
    private FavoriteDTO convertToDTO(Favorite favorite) {
        return new FavoriteDTO(
            favorite.getFavoriteId(),
            favorite.getRecipeId(),
            favorite.getUserId(),
            favorite.getCreatedAt()
        );
    }
    
    /**
     * Convierte una entidad Favorite a FavoriteDTO con información de receta.
     * 
     * @param favorite Entidad Favorite
     * @return FavoriteDTO con información de receta
     */
    private FavoriteDTO convertToDTOWithRecipeInfo(Favorite favorite) {
        Recipe recipe = favorite.getRecipe();
        return new FavoriteDTO(
            favorite.getFavoriteId(),
            favorite.getRecipeId(),
            favorite.getUserId(),
            favorite.getCreatedAt(),
            recipe != null ? recipe.getTitle() : null,
            recipe != null ? recipe.getDescription() : null
        );
    }
}
