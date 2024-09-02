package com.example.demo.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Tratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "id_mascota", nullable = false)
    private Pet mascota;

    @ManyToOne
    @JoinColumn(name = "cedula_veterinario", nullable = false)
    private Veterinario veterinario;

    @ManyToOne
    @JoinColumn(name = "nombre_medicamento", nullable = false)
    private Medicamento medicamento;

    // Constructor vacío
    public Tratamiento() {}

    // Constructor con campos
    public Tratamiento(Date fecha, Pet mascota, Veterinario veterinario, Medicamento medicamento) {
        this.fecha = fecha;
        this.mascota = mascota;
        this.veterinario = veterinario;
        this.medicamento = medicamento;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Pet getMascota() {
        return mascota;
    }

    public void setMascota(Pet mascota) {
        this.mascota = mascota;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }
}
