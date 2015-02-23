/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eyem.bean;

import com.eyem.entity.Usuario;
import com.eyem.services.UsuarioService;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioBean implements Serializable {

    private String email;
    private String nombre;
    private int numGrupos;
    private String imagen;
    private List<Usuario> listaUsuario;
    //private String sessionData;

    @Autowired
    UsuarioService usuarioService;

    @PostConstruct
    public void inicializar() {

        listaUsuario = usuarioService.buscarTodos();
        nombre = listaUsuario.get(0).getNombre();

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

    public void actualizarDatosSession() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if (session != null) {
            System.out.println("Hay algo en session");
            if (null != session.getAttribute("TOMAYA")) {
                System.out.println(session.getAttribute("TOMAYA"));
            } else {
                System.out.println("TOMAYA == NULL");
            }
        } else {
            System.out.println("Session vacia.");
        }
    }

}
