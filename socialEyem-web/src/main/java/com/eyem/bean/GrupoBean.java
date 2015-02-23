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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Maria
 */
@Component
public class GrupoBean implements Serializable{
    
    @Autowired
    GrupoService grupoService;
    
    @Autowired
    UsuarioService usuarioService;
    
    private Long idGrupo;
    private String nombreGrupo;
    private Usuario creador;
    private List<Grupo> listaGrupo;
    private List<Usuario> listaUsuarios;
    private List<Usuario> usuariosSeleccionados;

    @PostConstruct
    public void inicializar(){
        listaGrupo= grupoService.findAllGrupoByEmailUser("maria@bu");
        System.out.println("----------------------------------------------"+listaGrupo.get(0).getNombreGrupo());
        listaUsuarios = usuarioService.buscarTodos();
        //Habría que tener todos los datos del usuario que esta creando el grupo
        //Como aun no tenemos bean de sesion me invento uno
        creador = new Usuario();
        creador.setEmail("maria@com");
        creador.setImagen("fotobonita");
        creador.setNumGrupos(1);
        creador.setNombre("maria");
        usuariosSeleccionados = new ArrayList<>();
    }
    
    public String crearGrupo(){
        Grupo grupo = new Grupo();
        grupo.setCreador(creador);
        grupo.setIdGrupo(System.currentTimeMillis());
        grupo.setNombreGrupo(nombreGrupo);
        grupo.setListaUsuarios(usuariosSeleccionados);
        grupoService.crearGrupo(grupo);
        return "misgrupos.xhtml";
    }
    
    public Long getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Long idGrupo) {
        this.idGrupo = idGrupo;
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
