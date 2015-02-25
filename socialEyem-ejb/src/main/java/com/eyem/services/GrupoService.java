/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eyem.services;

import com.eyem.entity.Grupo;
import com.eyem.repository.GrupoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service

public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;
    
     public Grupo crearGrupo(Grupo g){
        
        grupoRepository.insert(g);
        return g;
    }
     
     public Grupo buscarGrupoPorId(Long id){
         Grupo g = new Grupo();
         g=grupoRepository.buscarGrupoID(id);
         if(g == null){
             return null;
         }
         return g;
     }

     public List<Grupo> findAllGrupoByEmailUser(String emailUser){
         List<Grupo> listaGrupos = grupoRepository.findAllGrupoByEmailUser(emailUser);
         if(listaGrupos.isEmpty()){
             return null;
         }
         return listaGrupos; 
    }
}
