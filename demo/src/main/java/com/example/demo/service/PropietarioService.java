package com.example.demo.service;

import com.example.demo.model.Propietario;
import java.util.List;
import java.util.Optional;

public interface PropietarioService {
    List<Propietario> findAll();
    Optional<Propietario> findById(Long id);
    Optional<Propietario> findByCedula(String cedula);
    Propietario save(Propietario propietario);
    void deleteByCedula(String cedula);
}
