package com.recetapp.recetapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recetapp.recetapp.constants.LogConstants;
import com.recetapp.recetapp.constants.MessageConstants;
import com.recetapp.recetapp.dto.ApiResponse;
import com.recetapp.recetapp.dto.RecipeImageDTO;
import com.recetapp.recetapp.service.RecipeImageService;

/**
 * Controlador para manejar operaciones con imágenes de recetas.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@RestController
@RequestMapping("/api/recipe-images")
public class RecipeImageController {
    
    @Autowired
    private RecipeImageService recipeImageService;
    
    /**
     * Sube una nueva imagen para una receta.
     * 
     * @param recipeId ID de la receta
     * @param userId ID del usuario
     * @param imageUrl URL de la imagen
     * @return Imagen subida
     */
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<RecipeImageDTO>> uploadImage(
            @RequestParam Integer recipeId,
            @RequestParam Integer userId,
            @RequestParam String imageUrl) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "POST /api/recipe-images/upload - Uploading image for recipe: " + recipeId));
        
        try {
            RecipeImageDTO uploadedImage = recipeImageService.uploadImage(recipeId, userId, imageUrl);
            
            ApiResponse<RecipeImageDTO> response = ApiResponse.success(
                MessageConstants.RECIPE_IMAGE_UPLOADED_SUCCESSFULLY, 
                uploadedImage
            );
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Image uploaded with ID: " + uploadedImage.getImageId()));
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Error uploading image: " + e.getMessage()));
            
            ApiResponse<RecipeImageDTO> response = ApiResponse.error(
                MessageConstants.ERROR_UPLOADING_IMAGE + ": " + e.getMessage()
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Obtiene todas las imágenes de una receta.
     * 
     * @param recipeId ID de la receta
     * @return Lista de imágenes de la receta
     */
    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<ApiResponse<List<RecipeImageDTO>>> getImagesByRecipeId(@PathVariable Integer recipeId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "GET /api/recipe-images/recipe/" + recipeId + " - Retrieving recipe images"));
        
        try {
            List<RecipeImageDTO> images = recipeImageService.getImagesByRecipeId(recipeId);
            
            ApiResponse<List<RecipeImageDTO>> response = ApiResponse.success(
                MessageConstants.RECIPE_IMAGES_RETRIEVED_SUCCESSFULLY, 
                images
            );
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Retrieved " + images.size() + " images for recipe " + recipeId));
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Error retrieving images: " + e.getMessage()));
            
            ApiResponse<List<RecipeImageDTO>> response = ApiResponse.error(
                MessageConstants.ERROR_RETRIEVING_IMAGES + ": " + e.getMessage()
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Obtiene todas las imágenes subidas por un usuario.
     * 
     * @param userId ID del usuario
     * @return Lista de imágenes del usuario
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<RecipeImageDTO>>> getImagesByUserId(@PathVariable Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "GET /api/recipe-images/user/" + userId + " - Retrieving user images"));
        
        try {
            List<RecipeImageDTO> images = recipeImageService.getImagesByUserId(userId);
            
            ApiResponse<List<RecipeImageDTO>> response = ApiResponse.success(
                MessageConstants.USER_IMAGES_RETRIEVED_SUCCESSFULLY, 
                images
            );
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Retrieved " + images.size() + " images for user " + userId));
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Error retrieving user images: " + e.getMessage()));
            
            ApiResponse<List<RecipeImageDTO>> response = ApiResponse.error(
                MessageConstants.ERROR_RETRIEVING_IMAGES + ": " + e.getMessage()
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Obtiene la imagen principal de una receta.
     * 
     * @param recipeId ID de la receta
     * @return Imagen principal de la receta
     */
    @GetMapping("/recipe/{recipeId}/main")
    public ResponseEntity<ApiResponse<RecipeImageDTO>> getMainImageByRecipeId(@PathVariable Integer recipeId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "GET /api/recipe-images/recipe/" + recipeId + "/main - Retrieving main image"));
        
        try {
            RecipeImageDTO mainImage = recipeImageService.getMainImageByRecipeId(recipeId);
            
            if (mainImage != null) {
                ApiResponse<RecipeImageDTO> response = ApiResponse.success(
                    MessageConstants.MAIN_IMAGE_RETRIEVED_SUCCESSFULLY, 
                    mainImage
                );
                
                System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Main image retrieved: " + mainImage.getImageId()));
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<RecipeImageDTO> response = ApiResponse.success(
                    MessageConstants.NO_IMAGES_FOUND_FOR_RECIPE, 
                    null
                );
                
                return ResponseEntity.ok(response);
            }
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Error retrieving main image: " + e.getMessage()));
            
            ApiResponse<RecipeImageDTO> response = ApiResponse.error(
                MessageConstants.ERROR_RETRIEVING_IMAGES + ": " + e.getMessage()
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Elimina una imagen específica.
     * 
     * @param imageId ID de la imagen
     * @param userId ID del usuario (para verificar permisos)
     * @return Respuesta de confirmación
     */
    @DeleteMapping("/{imageId}")
    public ResponseEntity<ApiResponse<String>> deleteImage(
            @PathVariable Integer imageId,
            @RequestParam Integer userId) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "DELETE /api/recipe-images/" + imageId + " - Deleting image"));
        
        try {
            recipeImageService.deleteImage(imageId, userId);
            
            ApiResponse<String> response = ApiResponse.success(
                MessageConstants.RECIPE_IMAGE_DELETED_SUCCESSFULLY, 
                "Image with ID " + imageId + " has been successfully deleted"
            );
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Image deleted: " + imageId));
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Error deleting image: " + e.getMessage()));
            
            ApiResponse<String> response = ApiResponse.error(
                MessageConstants.ERROR_DELETING_IMAGE + ": " + e.getMessage()
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
}
