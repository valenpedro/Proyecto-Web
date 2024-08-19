package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Propietario {

    private String cedula;
    private String nombre;
    private String correo;
    private String celular;
    private String contrasena;
    private List<Pet> mascotas;

    // Constructor con todos los campos
    public Propietario(String cedula, String nombre, String correo, String celular, String contrasena) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.correo = correo;
        this.celular = celular;
        this.contrasena = contrasena;
        this.mascotas = new ArrayList<>();
    }

    // Constructor vacío
    public Propietario() {
        this.mascotas = new ArrayList<>();
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public List<Pet> getMascotas() {
        return mascotas;
    }

    public void setMascotas(List<Pet> mascotas) {
        this.mascotas = mascotas;
    }

    public void addPet(Pet pet) {
        this.mascotas.add(pet);
    }
}
