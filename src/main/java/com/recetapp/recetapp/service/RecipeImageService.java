package com.recetapp.recetapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recetapp.recetapp.dto.RecipeImageDTO;
import com.recetapp.recetapp.model.RecipeImage;
import com.recetapp.recetapp.repository.RecipeImageRepository;
import com.recetapp.recetapp.repository.RecipeRepository;

/**
 * Servicio para manejar operaciones con imágenes de recetas.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Service
public class RecipeImageService {
    
    @Autowired
    private RecipeImageRepository recipeImageRepository;
    
    @Autowired
    private RecipeRepository recipeRepository;
    
    /**
     * Sube una nueva imagen para una receta.
     * 
     * @param recipeId ID de la receta
     * @param userId ID del usuario
     * @param imageUrl URL de la imagen
     * @return DTO de la imagen subida
     */
    public RecipeImageDTO uploadImage(Integer recipeId, Integer userId, String imageUrl) {
        System.out.println("[RecipeImageService] Subiendo imagen para receta: " + recipeId + " del usuario: " + userId);
        
        try {
            // Verificar que la receta existe
            if (!recipeRepository.existsById(recipeId)) {
                throw new RuntimeException("Receta no encontrada con ID: " + recipeId);
            }
            
            // Crear nueva imagen
            RecipeImage recipeImage = new RecipeImage(recipeId, userId, imageUrl);
            RecipeImage savedImage = recipeImageRepository.save(recipeImage);
            
            System.out.println("[RecipeImageService] Imagen subida exitosamente con ID: " + savedImage.getImageId());
            return mapToDTO(savedImage);
            
        } catch (Exception e) {
            System.err.println("[RecipeImageService] Error subiendo imagen: " + e.getMessage());
            throw new RuntimeException("Error subiendo imagen: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene todas las imágenes de una receta.
     * 
     * @param recipeId ID de la receta
     * @return Lista de DTOs de imágenes
     */
    public List<RecipeImageDTO> getImagesByRecipeId(Integer recipeId) {
        System.out.println("[RecipeImageService] Obteniendo imágenes para receta: " + recipeId);
        
        try {
            List<RecipeImage> images = recipeImageRepository.findByRecipeIdOrderByUploadedAtDesc(recipeId);
            List<RecipeImageDTO> imageDTOs = images.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
            
            System.out.println("[RecipeImageService] Se encontraron " + imageDTOs.size() + " imágenes para la receta " + recipeId);
            return imageDTOs;
            
        } catch (Exception e) {
            System.err.println("[RecipeImageService] Error obteniendo imágenes: " + e.getMessage());
            throw new RuntimeException("Error obteniendo imágenes: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene todas las imágenes subidas por un usuario.
     * 
     * @param userId ID del usuario
     * @return Lista de DTOs de imágenes
     */
    public List<RecipeImageDTO> getImagesByUserId(Integer userId) {
        System.out.println("[RecipeImageService] Obteniendo imágenes del usuario: " + userId);
        
        try {
            List<RecipeImage> images = recipeImageRepository.findByUserIdOrderByUploadedAtDesc(userId);
            List<RecipeImageDTO> imageDTOs = images.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
            
            System.out.println("[RecipeImageService] Se encontraron " + imageDTOs.size() + " imágenes del usuario " + userId);
            return imageDTOs;
            
        } catch (Exception e) {
            System.err.println("[RecipeImageService] Error obteniendo imágenes: " + e.getMessage());
            throw new RuntimeException("Error obteniendo imágenes: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene la imagen principal de una receta.
     * 
     * @param recipeId ID de la receta
     * @return DTO de la imagen principal o null si no hay imágenes
     */
    public RecipeImageDTO getMainImageByRecipeId(Integer recipeId) {
        System.out.println("[RecipeImageService] Obteniendo imagen principal para receta: " + recipeId);
        
        try {
            RecipeImage mainImage = recipeImageRepository.findMainImageByRecipeId(recipeId);
            
            if (mainImage != null) {
                System.out.println("[RecipeImageService] Imagen principal encontrada: " + mainImage.getImageId());
                return mapToDTO(mainImage);
            } else {
                System.out.println("[RecipeImageService] No se encontraron imágenes para la receta " + recipeId);
                return null;
            }
            
        } catch (Exception e) {
            System.err.println("[RecipeImageService] Error obteniendo imagen principal: " + e.getMessage());
            throw new RuntimeException("Error obteniendo imagen principal: " + e.getMessage());
        }
    }
    
    /**
     * Elimina una imagen específica.
     * 
     * @param imageId ID de la imagen
     * @param userId ID del usuario (para verificar permisos)
     */
    public void deleteImage(Integer imageId, Integer userId) {
        System.out.println("[RecipeImageService] Eliminando imagen: " + imageId + " del usuario: " + userId);
        
        try {
            RecipeImage image = recipeImageRepository.findById(imageId)
                    .orElseThrow(() -> new RuntimeException("Imagen no encontrada con ID: " + imageId));
            
            // Verificar que el usuario es dueño de la imagen
            if (!image.getUserId().equals(userId)) {
                throw new RuntimeException("No tienes permisos para eliminar esta imagen");
            }
            
            recipeImageRepository.deleteById(imageId);
            System.out.println("[RecipeImageService] Imagen eliminada exitosamente: " + imageId);
            
        } catch (Exception e) {
            System.err.println("[RecipeImageService] Error eliminando imagen: " + e.getMessage());
            throw new RuntimeException("Error eliminando imagen: " + e.getMessage());
        }
    }
    
    /**
     * Elimina todas las imágenes de una receta.
     * 
     * @param recipeId ID de la receta
     */
    public void deleteImagesByRecipeId(Integer recipeId) {
        System.out.println("[RecipeImageService] Eliminando todas las imágenes de la receta: " + recipeId);
        
        try {
            long deletedCount = recipeImageRepository.countByRecipeId(recipeId);
            recipeImageRepository.deleteByRecipeId(recipeId);
            
            System.out.println("[RecipeImageService] Se eliminaron " + deletedCount + " imágenes de la receta " + recipeId);
            
        } catch (Exception e) {
            System.err.println("[RecipeImageService] Error eliminando imágenes: " + e.getMessage());
            throw new RuntimeException("Error eliminando imágenes: " + e.getMessage());
        }
    }
    
    /**
     * Mapea un modelo RecipeImage a un DTO.
     * 
     * @param recipeImage Modelo de imagen
     * @return DTO de imagen
     */
    private RecipeImageDTO mapToDTO(RecipeImage recipeImage) {
        return new RecipeImageDTO(
            recipeImage.getImageId(),
            recipeImage.getRecipeId(),
            recipeImage.getUserId(),
            recipeImage.getImageUrl(),
            recipeImage.getUploadedAt()
        );
    }
}
