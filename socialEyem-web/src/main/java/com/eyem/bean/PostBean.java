/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eyem.bean;

import com.eyem.entity.Post;
import com.eyem.entity.Usuario;
import com.eyem.services.PostService;
import com.eyem.services.UsuarioService;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Maria
 */
@Component
public class PostBean implements Serializable {

    @Autowired
    PostService postService;
    
    @Autowired
    UsuarioService usuarioService;

    private Long idPost;
    private String tipo;
    private String contenido;
    private String imagen;
    private String video;
    private List<String> mostradoPor;
    private List<Post> listaPost;
    private Usuario creador;
    private String fechaCreacion;

    @PostConstruct
    public void inicializar() {
        listaPost = postService.buscarTodos();
    }

    public void crearPost(String email, String tipo) {
        
        Post p = new Post();
        Usuario u = usuarioService.buscarPorEmail(email);
        p.setContenido(contenido);
        try {
            java.net.URL url = new java.net.URL(imagen);
            Logger.getLogger(PostBean.class.getName()).log(Level.SEVERE, null,url.toString() + " linked by " + u.getEmail());
        } catch (MalformedURLException ex) {
            Logger.getLogger(PostBean.class.getName()).log(Level.SEVERE, null, ex);
            imagen = null;
        }
        p.setImagen(imagen);
        p.setIdPost(System.currentTimeMillis());
        p.setTipo(tipo);
        p.setCreador(u);
        
        postService.crearPost(p);
        listaPost = postService.buscarTodos();
        contenido = null;
        imagen = null;
        
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<Post> getListaPost() {
        return listaPost;
    }

    public void setListaPost(List<Post> listaPost) {
        this.listaPost = listaPost;
    }

    public Long getIdPost() {
        return idPost;
    }

    public void setIdPost(Long idPost) {
        this.idPost = idPost;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public List<String> getMostradoPor() {
        return mostradoPor;
    }

    public void setMostradoPor(List<String> mostradoPor) {
        this.mostradoPor = mostradoPor;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

}
