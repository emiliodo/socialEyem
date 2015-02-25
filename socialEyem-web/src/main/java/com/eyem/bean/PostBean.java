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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private Usuario creador;
    private String fechaCreacion;
    private String userEmailPerfil;

    @PostConstruct
    public void init() {

    }

    public void borrarPost(Post p) {
        postService.deletePost(p);
    }

    public void replicarPost(Post p, String email) {

        Post reEyem;
        // buscar post por ID
        reEyem = postService.findPostById(p.getIdPost());
        if (reEyem != null) {
            // añadir email a la lista del campo post.compartidoPor
            reEyem.getMostradoPor().add(email);

            // borrar el post segun el ID
            postService.deletePost(p);
            // añadir el post temporal anteriormente creado

            postService.crearPost(reEyem);
        }
    }

    public boolean reyemAnterior(Long idPost, String email) {
        boolean prueba = postService.reyemAnterior(idPost, email);
        System.out.println("");
        return postService.reyemAnterior(idPost, email);
    }

    public void crearPost(String email, String tipo) {

        Post p = new Post();
        Usuario u = usuarioService.buscarPorEmail(email);
        p.setContenido(contenido);
        String pattern = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";
        List<String> mostraporvacio = new ArrayList<>();

        Pattern compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = compiledPattern.matcher(imagen);
        while (matcher.find()) {
            System.out.println(matcher.group());
            video = matcher.group();
            imagen = null;
            if (video.contains("/embed/")) {
                p.setVideo(video);
            }
        }
        if (null != imagen) {
            try {
                java.net.URL url = new java.net.URL(imagen);
                Logger.getLogger(PostBean.class.getName()).log(Level.SEVERE, null, url.toString() + " subido por " + u.getEmail());
            } catch (MalformedURLException ex) {
                Logger.getLogger(PostBean.class.getName()).log(Level.SEVERE, null, ex + " causado por " + u.getEmail());
                imagen = null;
            }
        }
        p.setImagen(imagen);
        p.setIdPost(System.currentTimeMillis());
        p.setTipo(tipo);
        p.setCreador(u);
        p.setMostradoPor(mostraporvacio);

        postService.crearPost(p);
        contenido = null;
        imagen = null;

    }

    public List<Post> listaPostPublicos() {
        List<Post> lista = postService.findPublicPost();
        if (lista == null || lista.isEmpty()) {
            return null;
        } else {
            return lista;
        }
    }

    public List<Post> listaPostUsuario(String email, String tipo) {
        return postService.findPostUser(email, tipo);
    }

    public List<Post> listaPostReplicados(String email) {
        return postService.findPostReplicados(email);
    }

    public String getUserEmailPerfil() {
        return userEmailPerfil;
    }

    public void setUserEmailPerfil(String userEmailPerfil) {
        this.userEmailPerfil = userEmailPerfil;
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

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }
    
    public String buscarUsuario(){
        return "verPerfil?faces-redirect=true";
    }
    
    public String dameImagenDe(String e){
        return usuarioService.buscarPorEmail(e).getImagen();
    }
    
        public String dameNombreDe(String e){
        return usuarioService.buscarPorEmail(e).getNombre();
    }

}
