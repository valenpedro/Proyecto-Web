package com.example.demo.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Veterinario {

    @Id
    private String cedula;

    private String nombre;
    private String especialidad;
    private int numAtenciones;
    private String contrasena;
    private String correo; // Añadimos el campo correo

    @OneToMany(mappedBy = "veterinario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tratamiento> tratamientos = new ArrayList<>();

    // Constructor vacío
    public Veterinario() {}

    // Constructor con campos
    public Veterinario(String cedula, String nombre, String especialidad, int numAtenciones, String contrasena, String correo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.numAtenciones = numAtenciones;
        this.contrasena = contrasena;
        this.correo = correo;
    }

    // Getters y Setters
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public int getNumAtenciones() {
        return numAtenciones;
    }

    public void setNumAtenciones(int numAtenciones) {
        this.numAtenciones = numAtenciones;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }
}
