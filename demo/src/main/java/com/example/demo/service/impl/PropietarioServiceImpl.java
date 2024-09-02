package com.example.demo.service.impl;

import com.example.demo.model.Propietario;
import com.example.demo.repository.PropietarioRepository;
import com.example.demo.service.PropietarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PropietarioServiceImpl implements PropietarioService {

    private final PropietarioRepository propietarioRepository;

    public PropietarioServiceImpl(PropietarioRepository propietarioRepository) {
        this.propietarioRepository = propietarioRepository;
    }

    @Override
    public List<Propietario> findAll() {
        return propietarioRepository.findAll();
    }

    @Override
    public Optional<Propietario> findById(Long id) {
        return propietarioRepository.findById(id);
    }

    @Override
    public Optional<Propietario> findByCedula(String cedula) {
        return propietarioRepository.findByCedula(cedula);
    }

    @Override
    public Propietario save(Propietario propietario) {
        return propietarioRepository.save(propietario);
    }

    @Override
    @Transactional
    public void deleteByCedula(String cedula) {
        propietarioRepository.deleteByCedula(cedula);
    }
}
