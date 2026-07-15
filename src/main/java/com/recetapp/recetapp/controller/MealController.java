package com.recetapp.recetapp.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recetapp.recetapp.dto.ApiResponse;
import com.recetapp.recetapp.dto.MealDTO;
import com.recetapp.recetapp.model.Meal;
import com.recetapp.recetapp.repository.MealRepository;

/**
 * Controlador para manejar las operaciones CRUD de tipos de comida.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@RestController
@RequestMapping("/api/meals")
@CrossOrigin(origins = "*")
public class MealController {
    
    @Autowired
    private MealRepository mealRepository;
    
    /**
     * Obtiene todos los tipos de comida.
     * 
     * @return Lista de todos los tipos de comida
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<MealDTO>>> getAllMeals() {
        try {
            List<Meal> meals = mealRepository.findAllByOrderByNameAsc();
            List<MealDTO> mealDTOs = meals.stream()
                    .map(meal -> new MealDTO(meal.getMealId(), meal.getName()))
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(ApiResponse.success("Tipos de comida obtenidos exitosamente", mealDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al obtener tipos de comida: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Obtiene un tipo de comida por su ID.
     * 
     * @param id ID del tipo de comida
     * @return Tipo de comida encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MealDTO>> getMealById(@PathVariable Integer id) {
        try {
            Optional<Meal> meal = mealRepository.findById(id);
            
            if (meal.isPresent()) {
                MealDTO mealDTO = new MealDTO(
                    meal.get().getMealId(), 
                    meal.get().getName()
                );
                return ResponseEntity.ok(ApiResponse.success("Tipo de comida encontrado", mealDTO));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Tipo de comida no encontrado", 404));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al obtener tipo de comida: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Crea un nuevo tipo de comida.
     * 
     * @param mealDTO DTO con los datos del tipo de comida
     * @return Tipo de comida creado
     */
    @PostMapping
    public ResponseEntity<ApiResponse<MealDTO>> createMeal(@RequestBody MealDTO mealDTO) {
        try {
            // Validar que el nombre no esté vacío
            if (mealDTO.getName() == null || mealDTO.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("El nombre del tipo de comida es obligatorio", 400));
            }
            
            // Verificar si ya existe un tipo de comida con ese nombre
            if (mealRepository.existsByName(mealDTO.getName().trim())) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Ya existe un tipo de comida con ese nombre", 400));
            }
            
            // Crear y guardar el tipo de comida
            Meal newMeal = new Meal(mealDTO.getName().trim());
            Meal savedMeal = mealRepository.save(newMeal);
            
            MealDTO savedMealDTO = new MealDTO(
                savedMeal.getMealId(), 
                savedMeal.getName()
            );
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Tipo de comida creado exitosamente", savedMealDTO, 201));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al crear tipo de comida: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Actualiza un tipo de comida existente.
     * 
     * @param id ID del tipo de comida a actualizar
     * @param mealDTO DTO con los nuevos datos
     * @return Tipo de comida actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MealDTO>> updateMeal(
            @PathVariable Integer id, 
            @RequestBody MealDTO mealDTO) {
        try {
            // Validar que el nombre no esté vacío
            if (mealDTO.getName() == null || mealDTO.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("El nombre del tipo de comida es obligatorio", 400));
            }
            
            Optional<Meal> existingMeal = mealRepository.findById(id);
            if (!existingMeal.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Tipo de comida no encontrado", 404));
            }
            
            // Verificar si ya existe otro tipo de comida con ese nombre
            if (!existingMeal.get().getName().equals(mealDTO.getName().trim()) &&
                mealRepository.existsByName(mealDTO.getName().trim())) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Ya existe otro tipo de comida con ese nombre", 400));
            }
            
            // Actualizar el tipo de comida
            Meal mealToUpdate = existingMeal.get();
            mealToUpdate.setName(mealDTO.getName().trim());
            Meal updatedMeal = mealRepository.save(mealToUpdate);
            
            MealDTO updatedMealDTO = new MealDTO(
                updatedMeal.getMealId(), 
                updatedMeal.getName()
            );
            
            return ResponseEntity.ok(ApiResponse.success("Tipo de comida actualizado exitosamente", updatedMealDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al actualizar tipo de comida: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Elimina un tipo de comida.
     * 
     * @param id ID del tipo de comida a eliminar
     * @return Respuesta de confirmación
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteMeal(@PathVariable Integer id) {
        try {
            Optional<Meal> meal = mealRepository.findById(id);
            if (!meal.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Tipo de comida no encontrado", 404));
            }
            
            // TODO: Verificar si el tipo de comida está siendo usado por alguna receta
            // Si está siendo usado, no permitir eliminarlo
            
            mealRepository.deleteById(id);
            
            return ResponseEntity.ok(ApiResponse.success("Tipo de comida eliminado exitosamente", 
                "Tipo de comida con ID " + id + " ha sido eliminado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al eliminar tipo de comida: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Busca tipos de comida por nombre (búsqueda parcial).
     * 
     * @param name Nombre parcial a buscar
     * @return Lista de tipos de comida que coinciden
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<MealDTO>>> searchMealsByName(
            @RequestParam String name) {
        try {
            if (name == null || name.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("El parámetro de búsqueda es obligatorio", 400));
            }
            
            List<Meal> meals = mealRepository.findByNameContainingIgnoreCase(name.trim());
            List<MealDTO> mealDTOs = meals.stream()
                    .map(meal -> new MealDTO(meal.getMealId(), meal.getName()))
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(ApiResponse.success("Búsqueda completada", mealDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error en la búsqueda: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Obtiene estadísticas de tipos de comida con conteo de recetas.
     * 
     * @return Lista de tipos de comida con su conteo de recetas
     */
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<List<Object[]>>> getMealStats() {
        try {
            List<Object[]> stats = mealRepository.findMealsWithRecipeCount();
            
            return ResponseEntity.ok(ApiResponse.success("Estadísticas obtenidas exitosamente", stats));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al obtener estadísticas: " + e.getMessage(), 500));
        }
    }
}
