package com.recetapp.recetapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.recetapp.recetapp.constants.LogConstants;
import com.recetapp.recetapp.constants.MessageConstants;
import com.recetapp.recetapp.dto.CommentDTO;
import com.recetapp.recetapp.model.Comment;
import com.recetapp.recetapp.model.Recipe;
import com.recetapp.recetapp.model.User;
import com.recetapp.recetapp.repository.CommentRepository;
import com.recetapp.recetapp.repository.RecipeRepository;
import com.recetapp.recetapp.repository.UserRepository;

/**
 * Servicio para manejar operaciones relacionadas con los comentarios de las recetas.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RecipeRepository recipeRepository;
    
    /**
     * Crea un nuevo comentario en una receta.
     * 
     * @param recipeId ID de la receta
     * @param userId ID del usuario
     * @param content Contenido del comentario
     * @return DTO del comentario creado
     */
    public CommentDTO createComment(Integer recipeId, Integer userId, String content) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
            "Creating comment for recipe " + recipeId + " by user " + userId));
        
        try {
            // Validar contenido del comentario
            if (content == null || content.trim().isEmpty()) {
                throw new RuntimeException("Comment content cannot be empty");
            }
            
            if (content.trim().length() > 1000) {
                throw new RuntimeException("Comment content cannot exceed 1000 characters");
            }
            
            // Verificar que el usuario existe
            if (!userRepository.existsById(userId)) {
                throw new RuntimeException(MessageConstants.USER_NOT_FOUND + userId);
            }
            
            // Verificar que la receta existe
            if (!recipeRepository.existsById(recipeId)) {
                throw new RuntimeException("Recipe not found with ID: " + recipeId);
            }
            
            // Crear y guardar el comentario
            Comment comment = new Comment(recipeId, userId, content.trim());
            Comment savedComment = commentRepository.save(comment);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
                "Comment created successfully with ID: " + savedComment.getCommentId()));
            
            return convertToDTO(savedComment);
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
                "Error creating comment: " + e.getMessage()));
            throw e;
        }
    }
    
    /**
     * Actualiza un comentario existente.
     * 
     * @param commentId ID del comentario
     * @param userId ID del usuario (para verificar autoría)
     * @param content Nuevo contenido del comentario
     * @return DTO del comentario actualizado
     */
    public CommentDTO updateComment(Integer commentId, Integer userId, String content) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
            "Updating comment " + commentId + " by user " + userId));
        
        try {
            // Validar contenido del comentario
            if (content == null || content.trim().isEmpty()) {
                throw new RuntimeException("Comment content cannot be empty");
            }
            
            if (content.trim().length() > 1000) {
                throw new RuntimeException("Comment content cannot exceed 1000 characters");
            }
            
            // Buscar el comentario
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new RuntimeException("Comment not found with ID: " + commentId));
            
            // Verificar que el usuario es el autor del comentario
            if (!comment.getUserId().equals(userId)) {
                throw new RuntimeException("User is not authorized to update this comment");
            }
            
            // Actualizar el contenido
            comment.setContent(content.trim());
            Comment updatedComment = commentRepository.save(comment);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
                "Comment updated successfully: " + commentId));
            
            return convertToDTO(updatedComment);
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
                "Error updating comment: " + e.getMessage()));
            throw e;
        }
    }
    
    /**
     * Elimina un comentario.
     * 
     * @param commentId ID del comentario
     * @param userId ID del usuario (para verificar autoría)
     */
    public void deleteComment(Integer commentId, Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
            "Deleting comment " + commentId + " by user " + userId));
        
        try {
            // Buscar el comentario
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new RuntimeException("Comment not found with ID: " + commentId));
            
            // Verificar que el usuario es el autor del comentario
            if (!comment.getUserId().equals(userId)) {
                throw new RuntimeException("User is not authorized to delete this comment");
            }
            
            // Eliminar el comentario
            commentRepository.delete(comment);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
                "Comment deleted successfully: " + commentId));
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
                "Error deleting comment: " + e.getMessage()));
            throw e;
        }
    }
    
    /**
     * Obtiene todos los comentarios de una receta.
     * 
     * @param recipeId ID de la receta
     * @return Lista de comentarios de la receta
     */
    public List<CommentDTO> getRecipeComments(Integer recipeId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
            "Getting comments for recipe " + recipeId));
        
        try {
            List<Comment> comments = commentRepository.findByRecipeIdOrderByCreatedAtDesc(recipeId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
                "Found " + comments.size() + " comments for recipe " + recipeId));
            
            return comments.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
                    
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
                "Error getting recipe comments: " + e.getMessage()));
            throw e;
        }
    }
    
    /**
     * Obtiene comentarios de una receta con paginación.
     * 
     * @param recipeId ID de la receta
     * @param page Número de página (0-based)
     * @param size Tamaño de la página
     * @return Página de comentarios
     */
    public Page<CommentDTO> getRecipeCommentsPaged(Integer recipeId, int page, int size) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
            "Getting comments for recipe " + recipeId + " (page " + page + ", size " + size + ")"));
        
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Comment> commentsPage = commentRepository.findByRecipeIdOrderByCreatedAtDesc(recipeId, pageable);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
                "Found " + commentsPage.getTotalElements() + " comments for recipe " + recipeId));
            
            return commentsPage.map(this::convertToDTO);
                    
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
                "Error getting recipe comments paged: " + e.getMessage()));
            throw e;
        }
    }
    
    /**
     * Obtiene comentarios de una receta con información de usuario.
     * 
     * @param recipeId ID de la receta
     * @return Lista de comentarios con información de usuario
     */
    public List<CommentDTO> getRecipeCommentsWithUser(Integer recipeId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
            "Getting comments with user info for recipe " + recipeId));
        
        try {
            List<Comment> comments = commentRepository.findByRecipeIdWithUser(recipeId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
                "Found " + comments.size() + " comments with user info for recipe " + recipeId));
            
            return comments.stream()
                    .map(this::convertToDTOWithUserInfo)
                    .collect(Collectors.toList());
                    
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
                "Error getting recipe comments with user info: " + e.getMessage()));
            throw e;
        }
    }
    
    /**
     * Obtiene todos los comentarios de un usuario.
     * 
     * @param userId ID del usuario
     * @return Lista de comentarios del usuario
     */
    public List<CommentDTO> getUserComments(Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
            "Getting comments for user " + userId));
        
        try {
            List<Comment> comments = commentRepository.findByUserIdOrderByCreatedAtDesc(userId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
                "Found " + comments.size() + " comments for user " + userId));
            
            return comments.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
                    
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
                "Error getting user comments: " + e.getMessage()));
            throw e;
        }
    }
    
    /**
     * Obtiene comentarios de un usuario con información de receta.
     * 
     * @param userId ID del usuario
     * @return Lista de comentarios con información de receta
     */
    public List<CommentDTO> getUserCommentsWithRecipe(Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
            "Getting comments with recipe info for user " + userId));
        
        try {
            List<Comment> comments = commentRepository.findByUserIdWithRecipe(userId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
                "Found " + comments.size() + " comments with recipe info for user " + userId));
            
            return comments.stream()
                    .map(this::convertToDTOWithRecipeInfo)
                    .collect(Collectors.toList());
                    
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
                "Error getting user comments with recipe info: " + e.getMessage()));
            throw e;
        }
    }
    
    /**
     * Busca comentarios por contenido.
     * 
     * @param content Texto a buscar
     * @return Lista de comentarios que contienen el texto
     */
    public List<CommentDTO> searchComments(String content) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
            "Searching comments with content: " + content));
        
        try {
            List<Comment> comments = commentRepository.findByContentContainingIgnoreCase(content);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
                "Found " + comments.size() + " comments matching content"));
            
            return comments.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
                    
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
                "Error searching comments: " + e.getMessage()));
            throw e;
        }
    }
    
    /**
     * Obtiene comentarios recientes.
     * 
     * @param days Número de días hacia atrás
     * @return Lista de comentarios recientes
     */
    public List<CommentDTO> getRecentComments(int days) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
            "Getting recent comments from last " + days + " days"));
        
        try {
            List<Object[]> results = commentRepository.findRecentCommentsWithUser(days);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
                "Found " + results.size() + " recent comments"));
            
            return results.stream()
                    .map(this::convertToDTOFromObjectArray)
                    .collect(Collectors.toList());
                    
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.COMMENT_SERVICE_PREFIX, 
                "Error getting recent comments: " + e.getMessage()));
            throw e;
        }
    }
    
    /**
     * Cuenta cuántos comentarios tiene una receta.
     * 
     * @param recipeId ID de la receta
     * @return Número de comentarios
     */
    public long getRecipeCommentsCount(Integer recipeId) {
        return commentRepository.countByRecipeId(recipeId);
    }
    
    /**
     * Cuenta cuántos comentarios ha hecho un usuario.
     * 
     * @param userId ID del usuario
     * @return Número de comentarios
     */
    public long getUserCommentsCount(Integer userId) {
        return commentRepository.countByUserId(userId);
    }
    
    /**
     * Convierte una entidad Comment a CommentDTO.
     * 
     * @param comment Entidad Comment
     * @return CommentDTO
     */
    private CommentDTO convertToDTO(Comment comment) {
        return new CommentDTO(
            comment.getCommentId(),
            comment.getRecipeId(),
            comment.getUserId(),
            comment.getContent(),
            comment.getCreatedAt()
        );
    }
    
    /**
     * Convierte una entidad Comment a CommentDTO con información de usuario.
     * 
     * @param comment Entidad Comment
     * @return CommentDTO con información de usuario
     */
    private CommentDTO convertToDTOWithUserInfo(Comment comment) {
        User user = comment.getUser();
        return new CommentDTO(
            comment.getCommentId(),
            comment.getRecipeId(),
            comment.getUserId(),
            comment.getContent(),
            comment.getCreatedAt(),
            user != null ? user.getUsername() : null,
            user != null ? user.getEmail() : null
        );
    }
    
    /**
     * Convierte una entidad Comment a CommentDTO con información de receta.
     * 
     * @param comment Entidad Comment
     * @return CommentDTO con información de receta
     */
    private CommentDTO convertToDTOWithRecipeInfo(Comment comment) {
        Recipe recipe = comment.getRecipe();
        User user = comment.getUser();
        return new CommentDTO(
            comment.getCommentId(),
            comment.getRecipeId(),
            comment.getUserId(),
            comment.getContent(),
            comment.getCreatedAt(),
            user != null ? user.getUsername() : null,
            user != null ? user.getEmail() : null,
            recipe != null ? recipe.getTitle() : null
        );
    }
    
    /**
     * Convierte un array de objetos (resultado de consulta nativa) a CommentDTO.
     * 
     * @param result Array de objetos con datos del comentario y usuario
     * @return CommentDTO con información completa
     */
    private CommentDTO convertToDTOFromObjectArray(Object[] result) {
        // El orden de los campos en la consulta SQL es: 
        // c.commentId, c.recipeId, c.userId, c.content, c.createdAt, u.username, u.email
        Integer commentId = (Integer) result[0];
        Integer recipeId = (Integer) result[1];
        Integer userId = (Integer) result[2];
        String content = (String) result[3];
        
        // Manejar el casting de Timestamp a LocalDateTime
        LocalDateTime createdAt;
        if (result[4] instanceof java.sql.Timestamp) {
            java.sql.Timestamp timestamp = (java.sql.Timestamp) result[4];
            createdAt = timestamp.toLocalDateTime();
        } else {
            createdAt = (LocalDateTime) result[4];
        }
        
        String username = (String) result[5];
        String email = (String) result[6];
        
        return new CommentDTO(
            commentId,
            recipeId,
            userId,
            content,
            createdAt,
            username,
            email,
            null // recipeTitle no se incluye en esta consulta
        );
    }
}
