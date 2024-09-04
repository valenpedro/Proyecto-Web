package com.example.demo.service;

import com.example.demo.model.Veterinario;
import java.util.List;
import java.util.Optional;

public interface VeterinarioService {
    List<Veterinario> findAll();
    Optional<Veterinario> findByCedula(String cedula);
    Optional<Veterinario> findByCorreo(String correo); // Añadimos el método de buscar por correo
    Veterinario save(Veterinario veterinario);
    void deleteByCedula(String cedula);
}
