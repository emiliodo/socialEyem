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
public class VerPerfilBean implements Serializable {

    @Autowired
    PostService postService;
    @Autowired
    UsuarioService usuarioService;

    private Long idPost;
    private Usuario creador;
    private String userEmailPerfil;

    @PostConstruct
    public void init() {

    }

    public Long getIdPost() {
        return idPost;
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

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public String buscarUsuario() {
        return "verPerfil?faces-redirect=true";
    }

    public String dameImagenDe(String e) {
        return usuarioService.buscarPorEmail(e).getImagen();
    }

    public String dameNombreDe(String e) {
        return usuarioService.buscarPorEmail(e).getNombre();
    }

    public String dameEmailDe(String e) {
        return usuarioService.buscarPorEmail(e).getEmail();
    }

    public List<Post> listaPostUsuario(String email, String tipo) {
        return postService.findPostUser(email, tipo);
    }

    public List<Post> damePostDe(String e) {
        return postService.findPostUser(e, "publico");
    }

}
