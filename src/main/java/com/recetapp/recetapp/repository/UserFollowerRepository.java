package com.recetapp.recetapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.recetapp.recetapp.model.UserFollower;

/**
 * Repository para manejar las operaciones de followers en la base de datos.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Repository
public interface UserFollowerRepository extends JpaRepository<UserFollower, Long> {
    
    /**
     * Verifica si un usuario sigue a otro
     */
    boolean existsByFollowerUserIdAndFollowedUserId(Integer followerId, Integer followedId);
    
    /**
     * Encuentra la relación de seguimiento entre dos usuarios
     */
    Optional<UserFollower> findByFollowerUserIdAndFollowedUserId(Integer followerId, Integer followedId);
    
    /**
     * Obtiene todos los usuarios que sigue un usuario específico
     */
    @Query("SELECT uf FROM UserFollower uf WHERE uf.follower.userId = :followerId")
    List<UserFollower> findByFollowerUserId(@Param("followerId") Integer followerId);
    
    /**
     * Obtiene todos los seguidores de un usuario específico
     */
    @Query("SELECT uf FROM UserFollower uf WHERE uf.followed.userId = :followedId")
    List<UserFollower> findByFollowedUserId(@Param("followedId") Integer followedId);
    
    /**
     * Obtiene solo los IDs de usuarios que sigue (más eficiente)
     */
    @Query("SELECT uf.followed.userId FROM UserFollower uf WHERE uf.follower.userId = :followerId")
    List<Integer> findFollowingUserIds(@Param("followerId") Integer followerId);
    
    /**
     * Obtiene solo los IDs de seguidores (más eficiente)
     */
    @Query("SELECT uf.follower.userId FROM UserFollower uf WHERE uf.followed.userId = :followedId")
    List<Integer> findFollowerUserIds(@Param("followedId") Integer followedId);
    
    /**
     * Cuenta cuántos usuarios sigue un usuario específico
     */
    long countByFollowerUserId(Integer followerId);
    
    /**
     * Cuenta cuántos seguidores tiene un usuario específico
     */
    long countByFollowedUserId(Integer followedId);
    
    /**
     * Elimina la relación de seguimiento entre dos usuarios
     */
    void deleteByFollowerUserIdAndFollowedUserId(Integer followerId, Integer followedId);
}
