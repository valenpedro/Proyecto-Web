package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Propietario;
import com.example.demo.repository.PropietarioRepository;


@Service
public class PropietarioService {

    @Autowired
    private PropietarioRepository propietarioRepository;

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
