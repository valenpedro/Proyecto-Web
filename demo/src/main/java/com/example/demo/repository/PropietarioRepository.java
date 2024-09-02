package com.example.demo.repository;

import com.example.demo.model.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropietarioRepository extends JpaRepository<Propietario, Long> {
    Optional<Propietario> findByCedula(String cedula);
    void deleteByCedula(String cedula);
}
