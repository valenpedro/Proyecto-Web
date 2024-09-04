package com.example.demo.service.impl;

import com.example.demo.model.Veterinario;
import com.example.demo.repository.VeterinarioRepository;
import com.example.demo.service.VeterinarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VeterinarioServiceImpl implements VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;

    public VeterinarioServiceImpl(VeterinarioRepository veterinarioRepository) {
        this.veterinarioRepository = veterinarioRepository;
    }

    @Override
    public List<Veterinario> findAll() {
        return veterinarioRepository.findAll();
    }

    @Override
    public Optional<Veterinario> findByCedula(String cedula) {
        return veterinarioRepository.findByCedula(cedula);
    }

    @Override
    public Optional<Veterinario> findByCorreo(String correo) {
        return veterinarioRepository.findByCorreo(correo);
    }

    @Override
    public Veterinario save(Veterinario veterinario) {
        return veterinarioRepository.save(veterinario);
    }

    @Override
    @Transactional
    public void deleteByCedula(String cedula) {
        veterinarioRepository.deleteByCedula(cedula);
    }
}
