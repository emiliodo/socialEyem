/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eyem.services;

import com.eyem.entity.Usuario;
import com.eyem.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service

public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<Usuario> buscarTodos(){
        return usuarioRepository.findAll();
    }
    
    public Usuario buscarPorEmail(String email){
        return (Usuario) usuarioRepository.findUsertByEmail(email);
    }
    
    public Usuario crearUsuario(String email, String nombre, String imagen){
        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setEmail(email);
        u.setImagen(imagen);
        u.setNumGrupos(0);
        usuarioRepository.insert(u);
        return u;
    }

}
