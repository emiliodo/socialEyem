/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eyem.bean;

import com.eyem.entity.Grupo;
import com.eyem.entity.Post;
import com.eyem.entity.Usuario;
import com.eyem.services.GrupoService;
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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Maria
 */
@Component
@Scope("request")
public class GrupoBean implements Serializable{
    
    @Autowired
    GrupoService grupoService;
    
    @Autowired
    UsuarioService usuarioService;
    
    @Autowired
    PostService postService;
    
    @Autowired 
    UsuarioBean usuarioBean;
    
    private Long idGrupo;
    private String nombreGrupo;
    private Usuario creador;
    private List<Grupo> listaGrupo;
    private List<Usuario> listaUsuarios;
    private List<Usuario> usuariosSeleccionados;
    private Grupo grupoSeleccionado;
    private List<Post> listaPostGrupo;
    
    //Posts
    private String contenido;
    private String imagen;
    private String video;
    private String userEmailPerfil;

    @PostConstruct
    public void inicializar(){
        System.out.println("ENTRo a postconstruct");
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String idParam = externalContext.getRequestParameterMap().get("id");
        
        ExternalContext externalContext2 = FacesContext.getCurrentInstance().getExternalContext();
        String idCrear = externalContext2.getRequestParameterMap().get("creandoPost");
        //Si hace el postConstruct desde mostrar grupos O DESDE CREAR POST DE GRUPO
        if(idParam == null || idParam.equals("")){
            //si entra creando un nuevo POST
            if(idCrear != null){
                idGrupo = Long.valueOf(idCrear);
                System.out.println("ENTRA A crear en el if del postConstruct "+ idGrupo.toString());
                grupoSeleccionado = new Grupo();
                grupoSeleccionado = grupoService.buscarGrupoPorId(idGrupo);
                System.out.println("EL NOMBRE DEL GRUPO ES "+grupoSeleccionado.getNombreGrupo());
                listaUsuarios = grupoSeleccionado.getListaUsuarios();
                listaPostGrupo = postService.buscarPostGrupo(idGrupo);
            }
            else{
                System.out.println("ENTRA A MOSTRAR TODOS LOS GRUPSO ID NULL");
                listaGrupo= grupoService.buscarGrupoPorUsuario(usuarioBean.getEmail());
                listaUsuarios = usuarioService.buscarTodos();
                creador = new Usuario();
                creador.setEmail(usuarioBean.getEmail());
                creador.setImagen(usuarioBean.getImagen());
                creador.setNombre(usuarioBean.getNombre());
                listaUsuarios.remove(creador);
                usuariosSeleccionados = new ArrayList<>();}
        }
        //Si entra habiendo clicado en un grupo
        else{
            idGrupo = Long.valueOf(idParam);
            System.out.println("ENTRA A MOSTRAR UN SOLO GRUPO "+ idGrupo.toString());
            grupoSeleccionado = new Grupo();
            grupoSeleccionado = grupoService.buscarGrupoPorId(idGrupo);
            System.out.println("EL NOMBRE DEL GRUPO ES "+grupoSeleccionado.getNombreGrupo());
            listaUsuarios = grupoSeleccionado.getListaUsuarios();
            listaPostGrupo = postService.buscarPostGrupo(idGrupo);
        }
    }
    
    public String crearGrupo(){
        Grupo grupo = new Grupo();
        grupo.setCreador(creador);
        grupo.setIdGrupo(System.currentTimeMillis());
        grupo.setNombreGrupo(nombreGrupo);
        usuariosSeleccionados.add(creador);
        grupo.setListaUsuarios(usuariosSeleccionados);
        grupoService.crearGrupo(grupo);
        
        //TENER EN CUENTA QUE EL ID? QUE LE PASO ES ID DE GRUPO Y NO ID 
         System.out.println("SJDFKJASDFKJ CREADO"+grupo.getIdGrupo().toString());
        return "mostrarGrupo?id="+grupo.getIdGrupo().toString()+"faces-redirect=true";
    }
    
    public void crearPostGrupo() {
        Post p = new Post();
        Usuario u = usuarioService.buscarPorEmail(usuarioBean.getEmail());
        p.setContenido(contenido);
        String pattern = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";

        Pattern compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = compiledPattern.matcher(imagen);
        while (matcher.find()) {
            System.out.println(matcher.group());
            video = matcher.group();
            imagen = null;
            p.setVideo(video);
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
        p.setTipo(idGrupo.toString());
        p.setCreador(u);

        postService.crearPost(p);
        contenido = null;
        imagen = null;
        listaPostGrupo = postService.buscarPostGrupo(idGrupo);

    }
    
    public void replicarPost(Post p, String email) {

        Post reEyem;
        // buscar post por ID
        reEyem = postService.findPostById(p.getIdPost());
        
        if (reEyem != null) {
            
            System.out.println("------------------------BUSCANDO POST POR ID "+p.getIdPost());
            System.out.println("CONTENIDO:::::::\n"+reEyem.getContenido());
            
      
//            // añadir email a la lista del campo post.compartidoPor
            reEyem.getMostradoPor().add(email);
            
//            // borrar el post segun el ID
            //postService.deletePostById(p.getIdPost());
//            // añadir el post temporal anteriormente creado
            //System.out.println("-----------------------CREANDO POST NUEVOoooooooooooooo");
            postService.crearPost(reEyem);
        }
    }
    
     public String verPerfil(String e) {
        this.userEmailPerfil = e;
        return "verPerfil";
    }
     
    public Long getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Long idGrupo) {
        this.idGrupo = idGrupo;
    }

    public List<Post> getListaPostGrupo() {
        return listaPostGrupo;
    }

    public void setListaPostGrupo(List<Post> listaPostGrupo) {
        this.listaPostGrupo = listaPostGrupo;
    }

    public Grupo getGrupoSeleccionado() {
        return grupoSeleccionado;
    }

    public void setGrupoSeleccionado(Grupo grupoSeleccionado) {
        this.grupoSeleccionado = grupoSeleccionado;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public List<Usuario> getUsuariosSeleccionados() {
        return usuariosSeleccionados;
    }

    public void setUsuariosSeleccionados(List<Usuario> usuariosSeleccionados) {
        this.usuariosSeleccionados = usuariosSeleccionados;
    }

    public Usuario getCreador() {
        return creador;
    }

    public List<Grupo> getListaGrupo() {
        return listaGrupo;
    }

    public void setListaGrupo(List<Grupo> listaGrupo) {
        this.listaGrupo = listaGrupo;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
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

    public String getUserEmailPerfil() {
        return userEmailPerfil;
    }

    public void setUserEmailPerfil(String userEmailPerfil) {
        this.userEmailPerfil = userEmailPerfil;
    }
    
    
}
