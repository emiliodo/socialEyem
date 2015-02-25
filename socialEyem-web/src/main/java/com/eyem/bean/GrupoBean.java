/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eyem.bean;

import com.eyem.entity.Grupo;
import com.eyem.entity.Usuario;
import com.eyem.services.GrupoService;
import com.eyem.services.UsuarioService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    UsuarioBean usuarioBean;
    
    private Long idGrupo;
    private String nombreGrupo;
    private Usuario creador;
    private List<Grupo> listaGrupo;
    private List<Usuario> listaUsuarios;
    private List<Usuario> usuariosSeleccionados;
    private Grupo grupoSeleccionado;

    @PostConstruct
    public void inicializar(){
        System.out.println("ENTRo a postconstruct");
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String idParam = externalContext.getRequestParameterMap().get("id");
        //Si hace el postConstruct desde mostrar grupos
        if(idParam == null || idParam.equals("")){
            System.out.println("ENTRA A MOSTRAR TODOS LOS GRUPSO ID NULL");
            listaGrupo= grupoService.findAllGrupoByEmailUser(usuarioBean.getEmail());
            listaUsuarios = usuarioService.buscarTodos();
            creador = new Usuario();
            creador.setEmail(usuarioBean.getEmail());
            creador.setImagen(usuarioBean.getImagen());
            creador.setNombre(usuarioBean.getNombre());
            listaUsuarios.remove(creador);
            usuariosSeleccionados = new ArrayList<>();
        }
        //Si entra habiendo clicado en un grupo
        else{
            idGrupo = Long.valueOf(idParam);
             System.out.println("ENTRA A MOSTRAR UN SOLO GRUPO "+ idGrupo.toString());
            grupoSeleccionado = new Grupo();
            grupoSeleccionado = grupoService.buscarGrupoPorId(idGrupo);
            System.out.println("EL NOMBRE DEL GRUPO ES "+grupoSeleccionado.getNombreGrupo());
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
    
    public Long getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Long idGrupo) {
        this.idGrupo = idGrupo;
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
    
    
}
