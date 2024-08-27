package com.example.demo.service;

import com.example.demo.model.Propietario;
import com.example.demo.repository.PropietarioRepository;
import org.springframework.stereotype.Service;

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

    public Optional<Propietario> findById(String cedula) {
        return propietarioRepository.findById(cedula);
    }

    public Propietario save(Propietario propietario) {
        return propietarioRepository.save(propietario);
    }

    public void deleteById(String cedula) {
        propietarioRepository.deleteById(cedula);
    }
}
