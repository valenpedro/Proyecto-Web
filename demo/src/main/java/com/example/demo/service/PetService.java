package com.example.demo.service;

import com.example.demo.model.Pet;
import com.example.demo.repository.PetRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
	@PersistenceContext
    private EntityManager entityManager;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
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
		
		// Verificar si la mascota existe
		Optional<Pet> petOpt = petRepository.findById(id);
		if (petOpt.isPresent()) {
			Pet pet = petOpt.get();
			
			// Eliminar la mascota usando EntityManager
			entityManager.remove(entityManager.contains(pet) ? pet : entityManager.merge(pet));
			entityManager.flush();

			// Forzar una consulta directa para confirmar la eliminación
			boolean exists = entityManager.createQuery("SELECT COUNT(p) FROM Pet p WHERE p.id = :id", Long.class)
										.setParameter("id", id)
										.getSingleResult() > 0;

			if (exists) {
				logger.error("Pet with id: {} still exists after deletion attempt.", id);
				throw new RuntimeException("Failed to delete pet with id: " + id);
			} else {
				logger.info("Pet with id: {} successfully deleted.", id);
			}
		} else {
			logger.warn("Pet with id: {} not found, cannot delete", id);
		}
	}

	
}
