package com.example.demo.repository;

import com.example.demo.model.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, String> {
    Optional<Veterinario> findByCedula(String cedula);
    void deleteByCedula(String cedula);
    Optional<Veterinario> findByCorreo(String correo); // Agregamos este método
}
