/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eyem.repository;
import com.eyem.entity.Post;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
/**
 *
 * @author YSF
 */
@Component
@Repository
public interface PostRepository extends MongoRepository<Post, Long>{
    
    @Query("{'creador.email':?0}")
    public List<Post> findAllPostByEmailUser(String emailUser);
    @Query("{'creador.email':?0,'tipo':?1}")
    public List<Post> findPostUser(String email, String tipo);
    @Query("{'tipo':'publico'}")
    public List<Post> findPublicPost();
    @Query("{'mostradoPor':?0}")
    public List<Post> findPostReplicados(String email);
    @Query("{'idPost':?0}")
    public List<Post> findPostById(Long id);
    
    @Query("{'tipo':'?0'}")
    public List<Post> buscarPostGrupo(Long idGrupo);
    
}
