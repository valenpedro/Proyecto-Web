package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String breed;
    private int age;
    private float weight;
    private String illness;
    private String photoUrl;
    private String status;

    @ManyToOne
    @JoinColumn(name = "propietario_cedula", nullable = false)
    private Propietario propietario; // Referencia al propietario

    // Constructor con todos los campos
    public Pet(String name, String breed, int age, float weight, String illness, String photoUrl, String status, Propietario propietario) {
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.weight = weight;
        this.illness = illness;
        this.photoUrl = photoUrl;
        this.status = status;
        this.propietario = propietario;
    }

    // Constructor vacío
    public Pet() {}

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }
}
