package com.recetapp.recetapp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recetapp.recetapp.dto.ApiResponse;
import com.recetapp.recetapp.dto.CategoryDTO;
import com.recetapp.recetapp.dto.MealDTO;
import com.recetapp.recetapp.model.RecipeCategory;
import com.recetapp.recetapp.model.RecipeMeal;
import com.recetapp.recetapp.repository.CategoryRepository;
import com.recetapp.recetapp.repository.MealRepository;
import com.recetapp.recetapp.repository.RecipeCategoryRepository;
import com.recetapp.recetapp.repository.RecipeMealRepository;
import com.recetapp.recetapp.repository.RecipeRepository;

/**
 * Controlador para vincular recetas con categorías y tipos de comida.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@RestController
@RequestMapping("/api/recipe-links")
@CrossOrigin(origins = "*")
public class RecipeLinkController {
    
    @Autowired
    private RecipeRepository recipeRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private MealRepository mealRepository;
    
    @Autowired
    private RecipeCategoryRepository recipeCategoryRepository;
    
    @Autowired
    private RecipeMealRepository recipeMealRepository;
    
    /**
     * Vincula categorías a una receta.
     * 
     * @param recipeId ID de la receta
     * @param categoryIds Lista de IDs de categorías
     * @return Respuesta de confirmación
     */
    @PostMapping("/{recipeId}/categories")
    public ResponseEntity<ApiResponse<String>> linkCategoriesToRecipe(
            @PathVariable Integer recipeId,
            @RequestBody List<Integer> categoryIds) {
        try {
            // Verificar que la receta existe
            if (!recipeRepository.existsById(recipeId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Receta no encontrada", 404));
            }
            
            // Verificar que las categorías existen
            for (Integer categoryId : categoryIds) {
                if (!categoryRepository.existsById(categoryId)) {
                    return ResponseEntity.badRequest()
                            .body(ApiResponse.error("Categoría con ID " + categoryId + " no encontrada", 400));
                }
            }
            
            // Eliminar categorías existentes
            recipeCategoryRepository.deleteByRecipeId(recipeId);
            
            // Vincular nuevas categorías
            for (Integer categoryId : categoryIds) {
                RecipeCategory recipeCategory = new RecipeCategory(recipeId, categoryId);
                recipeCategoryRepository.save(recipeCategory);
            }
            
            return ResponseEntity.ok(ApiResponse.success("Categorías vinculadas exitosamente", 
                "Se vincularon " + categoryIds.size() + " categorías a la receta"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al vincular categorías: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Vincula tipos de comida a una receta.
     * 
     * @param recipeId ID de la receta
     * @param mealIds Lista de IDs de tipos de comida
     * @return Respuesta de confirmación
     */
    @PostMapping("/{recipeId}/meals")
    public ResponseEntity<ApiResponse<String>> linkMealsToRecipe(
            @PathVariable Integer recipeId,
            @RequestBody List<Integer> mealIds) {
        try {
            // Verificar que la receta existe
            if (!recipeRepository.existsById(recipeId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Receta no encontrada", 404));
            }
            
            // Verificar que los tipos de comida existen
            for (Integer mealId : mealIds) {
                if (!mealRepository.existsById(mealId)) {
                    return ResponseEntity.badRequest()
                            .body(ApiResponse.error("Tipo de comida con ID " + mealId + " no encontrado", 400));
                }
            }
            
            // Eliminar tipos de comida existentes
            recipeMealRepository.deleteByRecipeId(recipeId);
            
            // Vincular nuevos tipos de comida
            for (Integer mealId : mealIds) {
                RecipeMeal recipeMeal = new RecipeMeal(recipeId, mealId);
                recipeMealRepository.save(recipeMeal);
            }
            
            return ResponseEntity.ok(ApiResponse.success("Tipos de comida vinculados exitosamente", 
                "Se vincularon " + mealIds.size() + " tipos de comida a la receta"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al vincular tipos de comida: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Vincula tanto categorías como tipos de comida a una receta.
     * 
     * @param recipeId ID de la receta
     * @param request DTO con categorías y tipos de comida
     * @return Respuesta de confirmación
     */
    @PostMapping("/{recipeId}/all")
    public ResponseEntity<ApiResponse<String>> linkAllToRecipe(
            @PathVariable Integer recipeId,
            @RequestBody RecipeLinkRequest request) {
        try {
            // Verificar que la receta existe
            if (!recipeRepository.existsById(recipeId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Receta no encontrada", 404));
            }
            
            // Verificar que las categorías existen
            if (request.getCategoryIds() != null) {
                for (Integer categoryId : request.getCategoryIds()) {
                    if (!categoryRepository.existsById(categoryId)) {
                        return ResponseEntity.badRequest()
                                .body(ApiResponse.error("Categoría con ID " + categoryId + " no encontrada", 400));
                    }
                }
            }
            
            // Verificar que los tipos de comida existen
            if (request.getMealIds() != null) {
                for (Integer mealId : request.getMealIds()) {
                    if (!mealRepository.existsById(mealId)) {
                        return ResponseEntity.badRequest()
                                .body(ApiResponse.error("Tipo de comida con ID " + mealId + " no encontrado", 400));
                    }
                }
            }
            
            // Eliminar vínculos existentes
            recipeCategoryRepository.deleteByRecipeId(recipeId);
            recipeMealRepository.deleteByRecipeId(recipeId);
            
            // Vincular nuevas categorías
            if (request.getCategoryIds() != null && !request.getCategoryIds().isEmpty()) {
                for (Integer categoryId : request.getCategoryIds()) {
                    RecipeCategory recipeCategory = new RecipeCategory(recipeId, categoryId);
                    recipeCategoryRepository.save(recipeCategory);
                }
            }
            
            // Vincular nuevos tipos de comida
            if (request.getMealIds() != null && !request.getMealIds().isEmpty()) {
                for (Integer mealId : request.getMealIds()) {
                    RecipeMeal recipeMeal = new RecipeMeal(recipeId, mealId);
                    recipeMealRepository.save(recipeMeal);
                }
            }
            
            return ResponseEntity.ok(ApiResponse.success("Vínculos creados exitosamente", 
                "Receta vinculada con " + 
                (request.getCategoryIds() != null ? request.getCategoryIds().size() : 0) + " categorías y " +
                (request.getMealIds() != null ? request.getMealIds().size() : 0) + " tipos de comida"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al crear vínculos: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Obtiene las categorías vinculadas a una receta.
     * 
     * @param recipeId ID de la receta
     * @return Lista de categorías vinculadas
     */
    @GetMapping("/{recipeId}/categories")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getRecipeCategories(@PathVariable Integer recipeId) {
        try {
            // Verificar que la receta existe
            if (!recipeRepository.existsById(recipeId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Receta no encontrada", 404));
            }
            
            List<RecipeCategory> recipeCategories = recipeCategoryRepository.findByRecipeId(recipeId);
            List<CategoryDTO> categories = recipeCategories.stream()
                    .map(rc -> new CategoryDTO(rc.getCategory().getCategoryId(), rc.getCategory().getName()))
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(ApiResponse.success("Categorías de la receta obtenidas exitosamente", categories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al obtener categorías de la receta: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Obtiene los tipos de comida vinculados a una receta.
     * 
     * @param recipeId ID de la receta
     * @return Lista de tipos de comida vinculados
     */
    @GetMapping("/{recipeId}/meals")
    public ResponseEntity<ApiResponse<List<MealDTO>>> getRecipeMeals(@PathVariable Integer recipeId) {
        try {
            // Verificar que la receta existe
            if (!recipeRepository.existsById(recipeId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Receta no encontrada", 404));
            }
            
            List<RecipeMeal> recipeMeals = recipeMealRepository.findByRecipeId(recipeId);
            List<MealDTO> meals = recipeMeals.stream()
                    .map(rm -> new MealDTO(rm.getMeal().getMealId(), rm.getMeal().getName()))
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(ApiResponse.success("Tipos de comida de la receta obtenidos exitosamente", meals));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al obtener tipos de comida de la receta: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Elimina todas las categorías de una receta.
     * 
     * @param recipeId ID de la receta
     * @return Respuesta de confirmación
     */
    @DeleteMapping("/{recipeId}/categories")
    public ResponseEntity<ApiResponse<String>> unlinkAllCategories(@PathVariable Integer recipeId) {
        try {
            // Verificar que la receta existe
            if (!recipeRepository.existsById(recipeId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Receta no encontrada", 404));
            }
            
            recipeCategoryRepository.deleteByRecipeId(recipeId);
            
            return ResponseEntity.ok(ApiResponse.success("Categorías desvinculadas exitosamente", 
                "Se eliminaron todas las categorías de la receta"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al desvincular categorías: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Elimina todos los tipos de comida de una receta.
     * 
     * @param recipeId ID de la receta
     * @return Respuesta de confirmación
     */
    @DeleteMapping("/{recipeId}/meals")
    public ResponseEntity<ApiResponse<String>> unlinkAllMeals(@PathVariable Integer recipeId) {
        try {
            // Verificar que la receta existe
            if (!recipeRepository.existsById(recipeId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Receta no encontrada", 404));
            }
            
            recipeMealRepository.deleteByRecipeId(recipeId);
            
            return ResponseEntity.ok(ApiResponse.success("Tipos de comida desvinculados exitosamente", 
                "Se eliminaron todos los tipos de comida de la receta"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al desvincular tipos de comida: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Elimina todos los vínculos de una receta.
     * 
     * @param recipeId ID de la receta
     * @return Respuesta de confirmación
     */
    @DeleteMapping("/{recipeId}/all")
    public ResponseEntity<ApiResponse<String>> unlinkAll(@PathVariable Integer recipeId) {
        try {
            // Verificar que la receta existe
            if (!recipeRepository.existsById(recipeId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Receta no encontrada", 404));
            }
            
            recipeCategoryRepository.deleteByRecipeId(recipeId);
            recipeMealRepository.deleteByRecipeId(recipeId);
            
            return ResponseEntity.ok(ApiResponse.success("Todos los vínculos eliminados exitosamente", 
                "Se eliminaron todas las categorías y tipos de comida de la receta"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al eliminar vínculos: " + e.getMessage(), 500));
        }
    }
    
    /**
     * DTO interno para solicitudes de vinculación.
     */
    public static class RecipeLinkRequest {
        private List<Integer> categoryIds;
        private List<Integer> mealIds;
        
        // Getters y Setters
        public List<Integer> getCategoryIds() { return categoryIds; }
        public void setCategoryIds(List<Integer> categoryIds) { this.categoryIds = categoryIds; }
        
        public List<Integer> getMealIds() { return mealIds; }
        public void setMealIds(List<Integer> mealIds) { this.mealIds = mealIds; }
    }
}
