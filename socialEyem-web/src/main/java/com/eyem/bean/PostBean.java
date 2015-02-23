/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eyem.bean;

import com.eyem.entity.Post;
import com.eyem.entity.Usuario;
import com.eyem.services.PostService;
import java.io.Serializable;
import java.util.List;
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

    private Long idPost;
    private String tipo;
    private String contenido;
    private String imagen;
    private String video;
    private List<String> mostradoPor;
    private List<Post> listaPost;
    private Usuario creador;

    @PostConstruct
    public void inicializar(){
//        listaPost = postService.buscarTodos();
//        contenido = listaPost.get(0).getContenido();
    }
    
    public void crearPost(){
        Post p = new Post();
        p.setContenido(contenido);
        postService.crearPost(p);
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
