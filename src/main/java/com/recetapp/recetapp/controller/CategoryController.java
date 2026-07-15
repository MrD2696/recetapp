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
import com.recetapp.recetapp.dto.CategoryDTO;
import com.recetapp.recetapp.model.Category;
import com.recetapp.recetapp.repository.CategoryRepository;

/**
 * Controlador para manejar las operaciones CRUD de categorías.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    /**
     * Obtiene todas las categorías.
     * 
     * @return Lista de todas las categorías
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getAllCategories() {
        try {
            List<Category> categories = categoryRepository.findAllByOrderByNameAsc();
            List<CategoryDTO> categoryDTOs = categories.stream()
                    .map(cat -> new CategoryDTO(cat.getCategoryId(), cat.getName()))
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(ApiResponse.success("Categorías obtenidas exitosamente", categoryDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al obtener categorías: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Obtiene una categoría por su ID.
     * 
     * @param id ID de la categoría
     * @return Categoría encontrada
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> getCategoryById(@PathVariable Integer id) {
        try {
            Optional<Category> category = categoryRepository.findById(id);
            
            if (category.isPresent()) {
                CategoryDTO categoryDTO = new CategoryDTO(
                    category.get().getCategoryId(), 
                    category.get().getName()
                );
                return ResponseEntity.ok(ApiResponse.success("Categoría encontrada", categoryDTO));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Categoría no encontrada", 404));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al obtener categoría: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Crea una nueva categoría.
     * 
     * @param categoryDTO DTO con los datos de la categoría
     * @return Categoría creada
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDTO>> createCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            // Validar que el nombre no esté vacío
            if (categoryDTO.getName() == null || categoryDTO.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("El nombre de la categoría es obligatorio", 400));
            }
            
            // Verificar si ya existe una categoría con ese nombre
            if (categoryRepository.existsByName(categoryDTO.getName().trim())) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Ya existe una categoría con ese nombre", 400));
            }
            
            // Crear y guardar la categoría
            Category newCategory = new Category(categoryDTO.getName().trim());
            Category savedCategory = categoryRepository.save(newCategory);
            
            CategoryDTO savedCategoryDTO = new CategoryDTO(
                savedCategory.getCategoryId(), 
                savedCategory.getName()
            );
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Categoría creada exitosamente", savedCategoryDTO, 201));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al crear categoría: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Actualiza una categoría existente.
     * 
     * @param id ID de la categoría a actualizar
     * @param categoryDTO DTO con los nuevos datos
     * @return Categoría actualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> updateCategory(
            @PathVariable Integer id, 
            @RequestBody CategoryDTO categoryDTO) {
        try {
            // Validar que el nombre no esté vacío
            if (categoryDTO.getName() == null || categoryDTO.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("El nombre de la categoría es obligatorio", 400));
            }
            
            Optional<Category> existingCategory = categoryRepository.findById(id);
            if (!existingCategory.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Categoría no encontrada", 404));
            }
            
            // Verificar si ya existe otra categoría con ese nombre
            if (!existingCategory.get().getName().equals(categoryDTO.getName().trim()) &&
                categoryRepository.existsByName(categoryDTO.getName().trim())) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Ya existe otra categoría con ese nombre", 400));
            }
            
            // Actualizar la categoría
            Category categoryToUpdate = existingCategory.get();
            categoryToUpdate.setName(categoryDTO.getName().trim());
            Category updatedCategory = categoryRepository.save(categoryToUpdate);
            
            CategoryDTO updatedCategoryDTO = new CategoryDTO(
                updatedCategory.getCategoryId(), 
                updatedCategory.getName()
            );
            
            return ResponseEntity.ok(ApiResponse.success("Categoría actualizada exitosamente", updatedCategoryDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al actualizar categoría: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Elimina una categoría.
     * 
     * @param id ID de la categoría a eliminar
     * @return Respuesta de confirmación
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCategory(@PathVariable Integer id) {
        try {
            Optional<Category> category = categoryRepository.findById(id);
            if (!category.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Categoría no encontrada", 404));
            }
            
            // TODO: Verificar si la categoría está siendo usada por alguna receta
            // Si está siendo usada, no permitir eliminarla
            
            categoryRepository.deleteById(id);
            
            return ResponseEntity.ok(ApiResponse.success("Categoría eliminada exitosamente", 
                "Categoría con ID " + id + " ha sido eliminada"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al eliminar categoría: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Busca categorías por nombre (búsqueda parcial).
     * 
     * @param name Nombre parcial a buscar
     * @return Lista de categorías que coinciden
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> searchCategoriesByName(
            @RequestParam String name) {
        try {
            if (name == null || name.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("El parámetro de búsqueda es obligatorio", 400));
            }
            
            List<Category> categories = categoryRepository.findByNameContainingIgnoreCase(name.trim());
            List<CategoryDTO> categoryDTOs = categories.stream()
                    .map(cat -> new CategoryDTO(cat.getCategoryId(), cat.getName()))
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(ApiResponse.success("Búsqueda completada", categoryDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error en la búsqueda: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Obtiene estadísticas de categorías con conteo de recetas.
     * 
     * @return Lista de categorías con su conteo de recetas
     */
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<List<Object[]>>> getCategoryStats() {
        try {
            List<Object[]> stats = categoryRepository.findCategoriesWithRecipeCount();
            
            return ResponseEntity.ok(ApiResponse.success("Estadísticas obtenidas exitosamente", stats));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al obtener estadísticas: " + e.getMessage(), 500));
        }
    }
}
