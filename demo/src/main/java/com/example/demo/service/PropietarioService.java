package com.example.demo.service;

import com.example.demo.model.Propietario;
import com.example.demo.repository.PropietarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PropietarioService {

    private final PropietarioRepository propietarioRepository;

    public PropietarioService(PropietarioRepository propietarioRepository) {
        this.propietarioRepository = propietarioRepository;
    }

    public List<Propietario> findAll() {
        return propietarioRepository.findAll();
    }

    public Optional<Propietario> findById(Long id) {
        return propietarioRepository.findById(id);
    }

    public Optional<Propietario> findByCedula(String cedula) {
        return propietarioRepository.findByCedula(cedula);
    }

    public Propietario save(Propietario propietario) {
        return propietarioRepository.save(propietario);
    }

    @Transactional
    public void deleteByCedula(String cedula) {
        propietarioRepository.deleteByCedula(cedula);
    }
}
