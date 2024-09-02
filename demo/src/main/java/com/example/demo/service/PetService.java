package com.example.demo.service;

import com.example.demo.model.Pet;
import java.util.List;
import java.util.Optional;

public interface PetService {
    List<Pet> getAllPets();
    Optional<Pet> getPetById(int id);
    void savePet(Pet pet);
    void deletePetById(int id);
}
