package com.example.demo.service;

import com.example.demo.model.Pet;
import com.example.demo.model.Propietario;
import com.example.demo.repository.PetRepository;
import com.example.demo.repository.TratamientoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private static final Logger logger = LoggerFactory.getLogger(PetService.class);

    private final PetRepository petRepository;
    private final PropietarioService propietarioService;
    private final TratamientoRepository tratamientoRepository;

    public PetService(PetRepository petRepository, PropietarioService propietarioService, TratamientoRepository tratamientoRepository) {
        this.petRepository = petRepository;
        this.propietarioService = propietarioService;
        this.tratamientoRepository = tratamientoRepository;
    }

    public List<Pet> getAllPets() {
        logger.info("Fetching all pets from the database");
        return petRepository.findAll();
    }

    public Optional<Pet> getPetById(int id) {
        logger.info("Fetching pet with id: {}", id);
        return petRepository.findById(id);
    }

    public void savePet(Pet pet) {
        logger.info("Saving pet: {}", pet);
        petRepository.save(pet);
    }

    @Transactional
    public void deletePetById(int id) {
        logger.info("Attempting to delete pet with id: {}", id);

        Optional<Pet> petOpt = petRepository.findById(id);
        if (petOpt.isPresent()) {
            Pet pet = petOpt.get();

            // Eliminar tratamientos asociados antes de eliminar la mascota
            tratamientoRepository.deleteByMascota(pet);

            // Eliminar la mascota de la lista de mascotas del propietario
            Propietario propietario = pet.getPropietario();
            propietario.getMascotas().remove(pet);
            propietarioService.save(propietario);

            // Ahora eliminar la mascota de la base de datos
            petRepository.deleteById(id);
            logger.info("Pet with id: {} deleted successfully", id);
        } else {
            logger.warn("Pet with id: {} not found, cannot delete", id);
        }
    }
}
