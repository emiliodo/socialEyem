/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eyem.repository;
import com.eyem.entity.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
/**
 *
 * @author YSF
 */
@Component
@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String>{
    
}
