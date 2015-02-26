/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eyem.repository;

import com.eyem.entity.Grupo;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface GrupoRepository extends MongoRepository<Grupo, String>{
    @Query("{'listaUsuarios.email':?0}")
    public List<Grupo> buscarGrupoPorUsuario(String emailUser);
    
    @Query("{'idGrupo':?0}")
    public Grupo buscarGrupoID(Long id);
}
