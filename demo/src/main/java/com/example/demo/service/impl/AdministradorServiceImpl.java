package com.example.demo.service.impl;

import com.example.demo.model.Administrador;
import com.example.demo.repository.AdministradorRepository;
import com.example.demo.service.AdministradorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Asegúrate de que esté correctamente anotado
public class AdministradorServiceImpl implements AdministradorService {

    private final AdministradorRepository administradorRepository;

    public AdministradorServiceImpl(AdministradorRepository administradorRepository) {
        this.administradorRepository = administradorRepository;
    }

    @Override
    public List<Administrador> findAll() {
        return administradorRepository.findAll();
    }

    @Override
    public Optional<Administrador> findByCedula(String cedula) {
        return administradorRepository.findById(Long.valueOf(cedula));
    }

    @Override
    public Administrador save(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    @Override
    public void deleteByCedula(String cedula) {
        administradorRepository.deleteById(Long.valueOf(cedula));
    }
}
