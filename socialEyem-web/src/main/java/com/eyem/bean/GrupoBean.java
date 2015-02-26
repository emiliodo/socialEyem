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
    private List<Usuario> usuariosParaAnadir;
    private Grupo grupoSeleccionado;
    private List<Post> listaPostGrupo;
    private List<String> emailUsuariosSeleccionadosEliminar;
    private List<String> emailUsuariosSeleccionadosAnadir;
    //Posts
    private String contenido;
    private String imagen;
    private String video;
    private String userEmailPerfil;

    @PostConstruct
    public void inicializar(){
        
        creador = new Usuario();
        creador.setEmail(usuarioBean.getEmail());
        creador.setImagen(usuarioBean.getImagen());
        creador.setNombre(usuarioBean.getNombre());
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String idParam = externalContext.getRequestParameterMap().get("id");
        String idCrear = externalContext.getRequestParameterMap().get("creandoPost");
        String idEditar = externalContext.getRequestParameterMap().get("editandoGrupo");
        String idEditado = externalContext.getRequestParameterMap().get("grupoEditado");
        //Si hace el postConstruct desde mostrar grupos O DESDE CREAR POST DE GRUPO
        if(idParam == null || idParam.equals("")){
            //si entra creando un nuevo POST
            if(idCrear != null){
                idGrupo = Long.valueOf(idCrear);
                grupoSeleccionado = new Grupo();
                grupoSeleccionado = grupoService.buscarGrupoPorId(idGrupo);
                listaUsuarios = grupoSeleccionado.getListaUsuarios();
                listaPostGrupo = postService.buscarPostGrupo(idGrupo);
            }
            //editar grupo
            else if (idEditar != null){
                idGrupo = Long.valueOf(idEditar);
                listaGrupo = grupoService.buscarGrupoPorUsuario(creador.getEmail());
                grupoSeleccionado = new Grupo();
                grupoSeleccionado = grupoService.buscarGrupoPorId(idGrupo);
                //lista de usuarios que pertenecen al grupo
                listaUsuarios = grupoSeleccionado.getListaUsuarios();
                listaUsuarios.remove(creador);
                //listaUsuarios = todos los usuarios del grupo - el admin
                usuariosParaAnadir = usuarioService.buscarTodos();
                usuariosParaAnadir.removeAll(listaUsuarios);
                usuariosParaAnadir.remove(creador);
                nombreGrupo = grupoSeleccionado.getNombreGrupo();
                //para eliminar
                usuariosSeleccionados = new ArrayList<>();
                //usuariosSeleccionadosAnadir = new ArrayList<>();
            }
            else if(idEditado != null){
                idGrupo = Long.valueOf(idEditado);
                listaGrupo= grupoService.buscarGrupoPorUsuario(usuarioBean.getEmail());
                //listaUsuarios = usuarioService.buscarTodos();
                listaUsuarios = grupoService.buscarGrupoPorId(idGrupo).getListaUsuarios();
                listaUsuarios.remove(creador);
                usuariosSeleccionados = new ArrayList<>();
            }
            else{
                listaGrupo= grupoService.buscarGrupoPorUsuario(usuarioBean.getEmail());
                listaUsuarios = usuarioService.buscarTodos();
                listaUsuarios.remove(creador);
                usuariosSeleccionados = new ArrayList<>();
            }
        }
        //Si entra habiendo clicado en un grupo
        else{
            idGrupo = Long.valueOf(idParam);
            grupoSeleccionado = new Grupo();
            grupoSeleccionado = grupoService.buscarGrupoPorId(idGrupo);
            listaUsuarios = grupoSeleccionado.getListaUsuarios();
            listaPostGrupo = postService.buscarPostGrupo(idGrupo);
        }
    }
    
    public String crearGrupo(){
        this.usuariosSeleccionados = emailsToUsuarios(this.emailUsuariosSeleccionadosEliminar, listaUsuarios);
        Grupo grupo = new Grupo();
        grupo.setCreador(creador);
        grupo.setIdGrupo(System.currentTimeMillis());
        grupo.setNombreGrupo(nombreGrupo);
        usuariosSeleccionados.add(creador);
        grupo.setListaUsuarios(usuariosSeleccionados);
        grupoService.crearGrupo(grupo);
        return "mostrarGrupo?id="+grupo.getIdGrupo().toString()+"faces-redirect=true";
    }
    
    
    public String borrarGrupo(){
        Grupo grupoBorrar = grupoService.buscarGrupoPorId(idGrupo);
        grupoService.borrarGrupo(grupoBorrar);
        return "misgrupos?faces-redirect=true";
    }
    
    public String editarGrupo(){
        // Lista auxiliar para meter todos los usuarios d un grupo
        // y eliminar de ella los seleccionados que quiero eliminar
        List<Usuario> listaAux = listaUsuarios;
        if (this.emailUsuariosSeleccionadosEliminar != null) {
            listaAux = this.eliminarUsuariosDeGrupo(listaUsuarios);
        }             
        if (this.emailUsuariosSeleccionadosAnadir != null) {
            listaAux = this.anadirUsuariosAGrupo(listaAux);
        }
        Grupo grupoBorrar = grupoService.buscarGrupoPorId(idGrupo);
        grupoService.borrarGrupo(grupoBorrar);
        Grupo grupo = new Grupo();
        grupo.setCreador(creador);
        grupo.setIdGrupo(idGrupo);
        grupo.setNombreGrupo(nombreGrupo);
        listaAux.add(creador);
        grupo.setListaUsuarios(listaAux);
        grupoService.crearGrupo(grupo);
        //Para que la muestre
        listaUsuarios = listaAux;
        return "mostrarGrupo?id="+grupo.getIdGrupo().toString()+"faces-redirect=true";
    }
    
     public String salirGrupo(){
        Grupo grupoEditar = grupoService.buscarGrupoPorId(idGrupo);
        Grupo grupo = new Grupo();
        grupo.setCreador(grupoEditar.getCreador());
        grupo.setIdGrupo(grupoEditar.getIdGrupo());
        grupo.setNombreGrupo(grupoEditar.getNombreGrupo());
        List<Usuario> lUsuario = grupoEditar.getListaUsuarios();
        lUsuario.remove(creador);
        grupo.setListaUsuarios(lUsuario);
        grupoService.crearGrupo(grupo);
        grupoService.borrarGrupo(grupoEditar);
        return "misgrupos?faces-redirect=true";
    }    
        
    public void crearPostGrupo() {
        Post p = new Post();
        Usuario u = usuarioService.buscarPorEmail(usuarioBean.getEmail());
        p.setContenido(contenido);
        String pattern = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";

        Pattern compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = compiledPattern.matcher(imagen);
        while (matcher.find()) {
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
    
    private List<Usuario> emailsToUsuarios (List<String>listEmails, List<Usuario> listaTodos) {
        List<Usuario> lista = new ArrayList<>();
        if (listEmails != null) {
            for (String email:listEmails) {
                for (Usuario user:listaTodos) {
                    if (user.getEmail().equals(email)) {
                        lista.add(user);
                        break;
                    }
                }
            }
        }
        return lista;
    }
    
    protected List<Usuario> eliminarUsuariosDeGrupo (List<Usuario> listaTodos) {
        List<Usuario> lista = listaTodos;
        this.usuariosSeleccionados = emailsToUsuarios(this.emailUsuariosSeleccionadosEliminar, this.listaUsuarios);
        lista.removeAll(usuariosSeleccionados); 
        return lista;
    }
    
    protected List<Usuario> anadirUsuariosAGrupo (List<Usuario> lista) {
        List<Usuario> listaSeleccionados;
        listaSeleccionados = emailsToUsuarios(emailUsuariosSeleccionadosAnadir, usuariosParaAnadir);
        lista.addAll(listaSeleccionados);      
        return lista;
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

    public List<String> getEmailUsuariosSeleccionadosEliminar() {
        return emailUsuariosSeleccionadosEliminar;
    }

    public void setEmailUsuariosSeleccionadosEliminar(List<String> emailUsuariosSeleccionados) {
        this.emailUsuariosSeleccionadosEliminar = emailUsuariosSeleccionados;
    }

    public List<String> getEmailUsuariosSeleccionadosAnadir() {
        return emailUsuariosSeleccionadosAnadir;
    }

    public void setEmailUsuariosSeleccionadosAnadir(List<String> emailUsuariosSeleccionadosAnadir) {
        this.emailUsuariosSeleccionadosAnadir = emailUsuariosSeleccionadosAnadir;
    }

    public List<Usuario> getUsuariosParaAnadir() {
        return usuariosParaAnadir;
    }

    public void setUsuariosParaAnadir(List<Usuario> usuariosParaAnadir) {
        this.usuariosParaAnadir = usuariosParaAnadir;
    }

    
    
}
