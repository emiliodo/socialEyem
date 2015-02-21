/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eyem.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author YSF
 */
@Document(collection = "usuario")
public class Usuario {

    @Id
    private String email;
    private String nombre;
    private int numGrupos;
    private String imagen;

    public Usuario() {
    }
    
     public Usuario(String email, String imagen, String nombre, int numGrupos) {
        this.email = email;
        this.imagen = imagen;
        this.nombre = nombre;
        this.numGrupos = numGrupos;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumGrupos() {
        return numGrupos;
    }

    public void setNumGrupos(int numGrupos) {
        this.numGrupos = numGrupos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
