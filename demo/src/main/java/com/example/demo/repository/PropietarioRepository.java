package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Propietario;

@Repository
public class PropietarioRepository {

    private List<Propietario> propietarios;

    public PropietarioRepository() {
        propietarios = new ArrayList<>();
        propietarios.add(new Propietario("123456789", "Natalia", "z5E7T@example.com", "1234567890","123"));    
        propietarios.add(new Propietario("987654321", "Jane Smith", "z5E7T@example.com", "1234567890","12345"));
    }

    public List<Propietario> findAll() {
        return propietarios;
    }

    public Optional<Propietario> findById(String cedula) {
        return propietarios.stream().filter(propietario -> propietario.getCedula().equals(cedula)).findFirst();
    }

    public Propietario save(Propietario propietario) {
        // Eliminar el propietario existente con la misma cédula si está presente
        propietarios.removeIf(p -> p.getCedula().equals(propietario.getCedula()));
        propietarios.add(propietario);
        return propietario;
    }

    public void deleteById(String cedula) {
        propietarios.removeIf(propietario -> propietario.getCedula().equals(cedula));
    }
}