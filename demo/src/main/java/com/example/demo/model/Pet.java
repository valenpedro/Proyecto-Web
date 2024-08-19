package com.example.demo.model;

public class Pet {

    private int id;
    private String name;
    private String breed;
    private int age;
    private float weight;
    private String illness;
    private String photoUrl;
    private String status;
    private String ownerId; // Guardar solo el ID del propietario

    // Constructor con todos los campos
    public Pet(int id, String name, String breed, int age, float weight, String illness, String photoUrl, String status, String ownerId) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.weight = weight;
        this.illness = illness;
        this.photoUrl = photoUrl;
        this.status = status;
        this.ownerId = ownerId;
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

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
