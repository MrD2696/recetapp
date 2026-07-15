package com.recetapp.recetapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.recetapp.recetapp.constants.LogConstants;
import com.recetapp.recetapp.dto.ApiResponse;
import com.recetapp.recetapp.dto.CommentDTO;
import com.recetapp.recetapp.dto.CreateCommentRequest;
import com.recetapp.recetapp.dto.UpdateCommentRequest;
import com.recetapp.recetapp.service.CommentService;

/**
 * Controlador para manejar operaciones relacionadas con los comentarios de las recetas.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
public class CommentController {
    
    @Autowired
    private CommentService commentService;
    
    /**
     * Crea un nuevo comentario en una receta.
     * 
     * @param request Solicitud con los datos del comentario
     * @return Respuesta con el comentario creado
     */
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CommentDTO>> createComment(@RequestBody CreateCommentRequest request) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "POST /api/comments/create - Creating comment for recipe " + request.getRecipeId() + " by user " + request.getUserId()));
        
        try {
            CommentDTO comment = commentService.createComment(request.getRecipeId(), request.getUserId(), request.getContent());
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Comment created successfully with ID: " + comment.getCommentId()));
            
            return ResponseEntity.ok(ApiResponse.success(
                "Comment created successfully", 
                comment
            ));
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error creating comment: " + e.getMessage()));
            
            return ResponseEntity.badRequest().body(ApiResponse.error(
                "Error creating comment: " + e.getMessage()
            ));
        }
    }
    
    /**
     * Actualiza un comentario existente.
     * 
     * @param commentId ID del comentario
     * @param request Solicitud con los datos de actualización
     * @return Respuesta con el comentario actualizado
     */
    @PutMapping("/update/{commentId}")
    public ResponseEntity<ApiResponse<CommentDTO>> updateComment(
            @PathVariable Integer commentId,
            @RequestBody UpdateCommentRequest request) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "PUT /api/comments/update/" + commentId + " - Updating comment by user " + request.getUserId()));
        
        try {
            CommentDTO comment = commentService.updateComment(commentId, request.getUserId(), request.getContent());
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Comment updated successfully: " + commentId));
            
            return ResponseEntity.ok(ApiResponse.success(
                "Comment updated successfully", 
                comment
            ));
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error updating comment: " + e.getMessage()));
            
            return ResponseEntity.badRequest().body(ApiResponse.error(
                "Error updating comment: " + e.getMessage()
            ));
        }
    }
    
    /**
     * Elimina un comentario.
     * 
     * @param commentId ID del comentario
     * @param userId ID del usuario (para verificar autoría)
     * @return Respuesta de confirmación
     */
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<ApiResponse<String>> deleteComment(
            @PathVariable Integer commentId,
            @RequestParam Integer userId) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "DELETE /api/comments/delete/" + commentId + " - Deleting comment by user " + userId));
        
        try {
            commentService.deleteComment(commentId, userId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Comment deleted successfully: " + commentId));
            
            return ResponseEntity.ok(ApiResponse.success(
                "Comment deleted successfully", 
                "Comment removed"
            ));
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error deleting comment: " + e.getMessage()));
            
            return ResponseEntity.badRequest().body(ApiResponse.error(
                "Error deleting comment: " + e.getMessage()
            ));
        }
    }
    
    /**
     * Obtiene todos los comentarios de una receta.
     * 
     * @param recipeId ID de la receta
     * @return Respuesta con la lista de comentarios
     */
    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<ApiResponse<List<CommentDTO>>> getRecipeComments(@PathVariable Integer recipeId) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "GET /api/comments/recipe/" + recipeId + " - Getting comments for recipe"));
        
        try {
            List<CommentDTO> comments = commentService.getRecipeComments(recipeId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Found " + comments.size() + " comments for recipe " + recipeId));
            
            return ResponseEntity.ok(ApiResponse.success(
                "Recipe comments retrieved successfully", 
                comments
            ));
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error getting recipe comments: " + e.getMessage()));
            
            return ResponseEntity.badRequest().body(ApiResponse.error(
                "Error getting recipe comments: " + e.getMessage()
            ));
        }
    }
    
    /**
     * Obtiene comentarios de una receta con paginación.
     * 
     * @param recipeId ID de la receta
     * @param page Número de página (0-based)
     * @param size Tamaño de la página
     * @return Respuesta con la página de comentarios
     */
    @GetMapping("/recipe/{recipeId}/paged")
    public ResponseEntity<ApiResponse<Page<CommentDTO>>> getRecipeCommentsPaged(
            @PathVariable Integer recipeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "GET /api/comments/recipe/" + recipeId + "/paged - Getting comments with pagination (page " + page + ", size " + size + ")"));
        
        try {
            Page<CommentDTO> commentsPage = commentService.getRecipeCommentsPaged(recipeId, page, size);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Found " + commentsPage.getTotalElements() + " comments for recipe " + recipeId));
            
            return ResponseEntity.ok(ApiResponse.success(
                "Recipe comments retrieved successfully with pagination", 
                commentsPage
            ));
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error getting recipe comments paged: " + e.getMessage()));
            
            return ResponseEntity.badRequest().body(ApiResponse.error(
                "Error getting recipe comments paged: " + e.getMessage()
            ));
        }
    }
    
    /**
     * Obtiene comentarios de una receta con información de usuario.
     * 
     * @param recipeId ID de la receta
     * @return Respuesta con la lista de comentarios con información de usuario
     */
    @GetMapping("/recipe/{recipeId}/with-user")
    public ResponseEntity<ApiResponse<List<CommentDTO>>> getRecipeCommentsWithUser(@PathVariable Integer recipeId) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "GET /api/comments/recipe/" + recipeId + "/with-user - Getting comments with user info for recipe"));
        
        try {
            List<CommentDTO> comments = commentService.getRecipeCommentsWithUser(recipeId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Found " + comments.size() + " comments with user info for recipe " + recipeId));
            
            return ResponseEntity.ok(ApiResponse.success(
                "Recipe comments with user info retrieved successfully", 
                comments
            ));
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error getting recipe comments with user info: " + e.getMessage()));
            
            return ResponseEntity.badRequest().body(ApiResponse.error(
                "Error getting recipe comments with user info: " + e.getMessage()
            ));
        }
    }
    
    /**
     * Obtiene todos los comentarios de un usuario.
     * 
     * @param userId ID del usuario
     * @return Respuesta con la lista de comentarios
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<CommentDTO>>> getUserComments(@PathVariable Integer userId) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "GET /api/comments/user/" + userId + " - Getting comments for user"));
        
        try {
            List<CommentDTO> comments = commentService.getUserComments(userId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Found " + comments.size() + " comments for user " + userId));
            
            return ResponseEntity.ok(ApiResponse.success(
                "User comments retrieved successfully", 
                comments
            ));
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error getting user comments: " + e.getMessage()));
            
            return ResponseEntity.badRequest().body(ApiResponse.error(
                "Error getting user comments: " + e.getMessage()
            ));
        }
    }
    
    /**
     * Obtiene comentarios de un usuario con información de receta.
     * 
     * @param userId ID del usuario
     * @return Respuesta con la lista de comentarios con información de receta
     */
    @GetMapping("/user/{userId}/with-recipe")
    public ResponseEntity<ApiResponse<List<CommentDTO>>> getUserCommentsWithRecipe(@PathVariable Integer userId) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "GET /api/comments/user/" + userId + "/with-recipe - Getting comments with recipe info for user"));
        
        try {
            List<CommentDTO> comments = commentService.getUserCommentsWithRecipe(userId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Found " + comments.size() + " comments with recipe info for user " + userId));
            
            return ResponseEntity.ok(ApiResponse.success(
                "User comments with recipe info retrieved successfully", 
                comments
            ));
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error getting user comments with recipe info: " + e.getMessage()));
            
            return ResponseEntity.badRequest().body(ApiResponse.error(
                "Error getting user comments with recipe info: " + e.getMessage()
            ));
        }
    }
    
    /**
     * Busca comentarios por contenido.
     * 
     * @param content Texto a buscar
     * @return Respuesta con la lista de comentarios encontrados
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CommentDTO>>> searchComments(@RequestParam String content) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "GET /api/comments/search - Searching comments with content: " + content));
        
        try {
            List<CommentDTO> comments = commentService.searchComments(content);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Found " + comments.size() + " comments matching content"));
            
            return ResponseEntity.ok(ApiResponse.success(
                "Comments search completed successfully", 
                comments
            ));
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error searching comments: " + e.getMessage()));
            
            return ResponseEntity.badRequest().body(ApiResponse.error(
                "Error searching comments: " + e.getMessage()
            ));
        }
    }
    
    /**
     * Obtiene comentarios recientes.
     * 
     * @param days Número de días hacia atrás
     * @return Respuesta con la lista de comentarios recientes
     */
    @GetMapping("/recent")
    public ResponseEntity<ApiResponse<List<CommentDTO>>> getRecentComments(@RequestParam(defaultValue = "7") int days) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "GET /api/comments/recent - Getting recent comments from last " + days + " days"));
        
        try {
            List<CommentDTO> comments = commentService.getRecentComments(days);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Found " + comments.size() + " recent comments"));
            
            return ResponseEntity.ok(ApiResponse.success(
                "Recent comments retrieved successfully", 
                comments
            ));
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error getting recent comments: " + e.getMessage()));
            
            return ResponseEntity.badRequest().body(ApiResponse.error(
                "Error getting recent comments: " + e.getMessage()
            ));
        }
    }
    
    /**
     * Cuenta cuántos comentarios tiene una receta.
     * 
     * @param recipeId ID de la receta
     * @return Respuesta con el conteo de comentarios
     */
    @GetMapping("/recipe/{recipeId}/count")
    public ResponseEntity<ApiResponse<Long>> getRecipeCommentsCount(@PathVariable Integer recipeId) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "GET /api/comments/recipe/" + recipeId + "/count - Getting comments count for recipe"));
        
        try {
            long count = commentService.getRecipeCommentsCount(recipeId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Recipe " + recipeId + " has " + count + " comments"));
            
            return ResponseEntity.ok(ApiResponse.success(
                "Recipe comments count retrieved successfully", 
                count
            ));
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error getting recipe comments count: " + e.getMessage()));
            
            return ResponseEntity.badRequest().body(ApiResponse.error(
                "Error getting recipe comments count: " + e.getMessage()
            ));
        }
    }
    
    /**
     * Cuenta cuántos comentarios ha hecho un usuario.
     * 
     * @param userId ID del usuario
     * @return Respuesta con el conteo de comentarios
     */
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<ApiResponse<Long>> getUserCommentsCount(@PathVariable Integer userId) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "GET /api/comments/user/" + userId + "/count - Getting comments count for user"));
        
        try {
            long count = commentService.getUserCommentsCount(userId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "User " + userId + " has " + count + " comments"));
            
            return ResponseEntity.ok(ApiResponse.success(
                "User comments count retrieved successfully", 
                count
            ));
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error getting user comments count: " + e.getMessage()));
            
            return ResponseEntity.badRequest().body(ApiResponse.error(
                "Error getting user comments count: " + e.getMessage()
            ));
        }
    }
}
