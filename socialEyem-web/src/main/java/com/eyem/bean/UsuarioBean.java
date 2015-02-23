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
import javax.servlet.http.HttpServletRequest;
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

    public String actualizarDatosSession() {
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        this.email= request.getParameter("poiemail");
        this.nombre =  request.getParameter("poinombre");
        this.imagen =  request.getParameter("poiimagen");
        this.imagen = this.imagen.substring(0, this.imagen.length() - 5);
        return "timeline";
    }
    
    public void registrateOIniciaSesion(String email, String nombre, String img){
        Usuario temp = usuarioService.buscarPorEmail(email);
        if(temp!=null){
            this.email=email;
            this.imagen=temp.getImagen();
            this.nombre=temp.getNombre();
            this.numGrupos=temp.getNumGrupos();
        }else{
            usuarioService.crearUsuario(email, nombre, img);
        }
    }
    
    public String veteAIndex(){
        return "index";
    }

}
