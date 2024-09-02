package com.example.demo.repository;

import com.example.demo.model.Tratamiento;
import com.example.demo.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {
    void deleteByMascota(Pet mascota);
    List<Tratamiento> findByMascota(Pet mascota);
}
