package com.example.demo.repository;

import com.example.demo.model.Propietario;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PropietarioRepository {

    private final List<Propietario> propietarios;

    public PropietarioRepository() {
        this.propietarios = createPropietarios();
    }

    private List<Propietario> createPropietarios() {
        List<Propietario> propietarios = new ArrayList<>();
        propietarios.add(new Propietario("123456789", "Natalia", "natalia@example.com", "1234567890", "123"));
        propietarios.add(new Propietario("987654321", "Jane Smith", "jane.smith@example.com", "1234567890", "12345"));
        return propietarios;
    }

    public List<Propietario> findAll() {
        return propietarios;
    }

    public Optional<Propietario> findById(String cedula) {
        return propietarios.stream()
                .filter(prop -> prop.getCedula().equals(cedula))
                .findFirst();
    }

    public Propietario save(Propietario propietario) {
        propietarios.removeIf(p -> p.getCedula().equals(propietario.getCedula()));
        propietarios.add(propietario);
        return propietario;
    }

    public void deleteById(String cedula) {
        propietarios.removeIf(propietario -> propietario.getCedula().equals(cedula));
    }
}
