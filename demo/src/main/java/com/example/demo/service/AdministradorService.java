package com.example.demo.service;

import com.example.demo.model.Administrador;
import java.util.List;
import java.util.Optional;

public interface AdministradorService {
    List<Administrador> findAll();
    Optional<Administrador> findByCedula(String cedula);
    Administrador save(Administrador administrador);
    void deleteByCedula(String cedula);
}
