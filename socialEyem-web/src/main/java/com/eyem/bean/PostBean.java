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
    private Usuario creador;
    private String fechaCreacion;
    private String userEmailPerfil;

    @PostConstruct
    public void init() {

    }

    public void crearPost(String email, String tipo) {

        Post p = new Post();
        Usuario u = usuarioService.buscarPorEmail(email);
        p.setContenido(contenido);
        try {
            java.net.URL url = new java.net.URL(imagen);
            Logger.getLogger(PostBean.class.getName()).log(Level.SEVERE, null, url.toString() + " subido por " + u.getEmail());
        } catch (MalformedURLException ex) {
            Logger.getLogger(PostBean.class.getName()).log(Level.SEVERE, null, ex + " causado por " + u.getEmail());
            imagen = null;
        }
//        if(Patterns.WEB_URL.matcher(video).matches()){
//            p.setVideo(video);
//        }else{
//            p.setVideo(null);
//        }
        p.setImagen(imagen);
        p.setIdPost(System.currentTimeMillis());
        p.setTipo(tipo);
        p.setCreador(u);

        postService.crearPost(p);
        contenido = null;
        imagen = null;

    }

    public List<Post> listaPostPublicos() {
        return postService.findPublicPost();
    }

    public List<Post> listaPostUsuario(String email, String tipo) {
        return postService.findPostUser(email, tipo);
    }
    
    

    public String getUserEmailPerfil() {
        return userEmailPerfil;
    }

    public String verPerfil(String e) {
        this.userEmailPerfil = e;
        return "verPerfil";
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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
