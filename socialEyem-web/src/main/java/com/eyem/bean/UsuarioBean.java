/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eyem.bean;

import com.eyem.entity.Usuario;
import com.eyem.services.UsuarioService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UsuarioBean implements Serializable {
    
    private String email;
    private String nombre;
    private int numGrupos;
    private String imagen;
    private List<Usuario> listaUsuario;

    @Autowired
    UsuarioService usuarioService;

    @PostConstruct
    public void inicializar(){
        FacesContext context = FacesContext.getCurrentInstance();
        //String googeldata = (String) context.getExternalContext().getSessionMap().get("googleAccountData");
        listaUsuario = usuarioService.buscarTodos();
        nombre = listaUsuario.get(0).getNombre();
        System.out.println("datos google = "+ context.getExternalContext().getSessionMap().get("googleAccountData"));
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumGrupos() {
        return numGrupos;
    }

    public void setNumGrupos(int numGrupos) {
        this.numGrupos = numGrupos;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    
}
