package com.example.demo.repository;

import com.example.demo.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    Optional<Administrador> findByCedula(String cedula);
    void deleteByCedula(String cedula);
}
