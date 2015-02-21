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
@Document(collection = "grupo")
public class Grupo {

    private Long idGrupo;
    private String nombreGrupo;
    private Usuario creador;
    private List<Usuario> listaUsuarios;

    public Grupo(Long idGrupo, String nombreGrupo, Usuario creador, List<Usuario> listaUsuarios) {
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.creador = creador;
        this.listaUsuarios = listaUsuarios;
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

    public Usuario getCreador() {
        return creador;
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
