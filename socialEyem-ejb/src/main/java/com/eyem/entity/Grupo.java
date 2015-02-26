/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eyem.entity;

import java.util.List;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "grupo")
public class Grupo {
    
    @Id
    private String id;
    
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
    
    public Grupo(){
        
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Grupo other = (Grupo) obj;
        if (!Objects.equals(this.idGrupo, other.idGrupo)) {
            return false;
        }
        if (!Objects.equals(this.nombreGrupo, other.nombreGrupo)) {
            return false;
        }
        if (!Objects.equals(this.creador, other.creador)) {
            return false;
        }
        if (!Objects.equals(this.listaUsuarios, other.listaUsuarios)) {
            return false;
        }
        return true;
    }

}
