/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.eyem.entity;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author YSF
 */
@Document(collection="post")
public class Post implements Comparable<Post> {
 
 @Id
 private Long idPost;
 private String tipo;
 private String contenido;
 private String imagen;
 private String video;
 private List<String> mostradoPor;
 private Usuario creador;


    public Post(Long idPost, String tipo, String contenido, String imagen, String video, List<String> mostradoPor, Usuario creador) {
        this.idPost = idPost;
        this.tipo = tipo;
        this.contenido = contenido;
        this.imagen = imagen;
        this.video = video;
        this.mostradoPor = mostradoPor;
        this.creador = creador;
    }
    
    public Post(){
        
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

    @Override
    public int compareTo(Post o) {
        return this.getIdPost().compareTo(o.getIdPost());
    }
 
}
