package com.example.demo.repository;

import com.example.demo.model.Pet;
import com.example.demo.model.Propietario;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class PetRepository {
    private List<Pet> pets;

    private final PropietarioRepository propietarioRepository;

    public PetRepository(PropietarioRepository propietarioRepository) {
        this.propietarioRepository = propietarioRepository;
        List<Propietario> propietarios = propietarioRepository.findAll();
        if (propietarios.isEmpty()) {
            throw new IllegalArgumentException("La lista de propietarios no puede estar vacía");
        }
        this.pets = generateRandomPets(50, propietarios);
    }

    public List<Pet> findAll() {
        return pets;
    }

    public Pet findById(int id) {
        return pets.stream().filter(pet -> pet.getId() == id).findFirst().orElse(null);
    }

    public List<Pet> findByOwnerId(String ownerId) {
        return pets.stream().filter(pet -> pet.getOwnerId().equals(ownerId)).toList();
    }

    public void save(Pet pet) {
        // Verificar si la mascota ya existe (editar)
        int index = findIndexById(pet.getId());
        if (index == -1) {
            // Nueva mascota
            pet.setId(pets.size() + 1);
            pets.add(pet);
            // Agregar la mascota a la lista del propietario
            Propietario propietario = propietarioRepository.findById(pet.getOwnerId()).orElse(null);
            if (propietario != null) {
                propietario.addPet(pet);
                propietarioRepository.save(propietario);
            }
        } else {
            // Mascota existente, verificar si hay cambio de propietario
            Pet existingPet = pets.get(index);
            if (!existingPet.getOwnerId().equals(pet.getOwnerId())) {
                // Quitar la mascota de la lista del propietario anterior
                Propietario oldOwner = propietarioRepository.findById(existingPet.getOwnerId()).orElse(null);
                if (oldOwner != null) {
                    oldOwner.getMascotas().remove(existingPet);
                    propietarioRepository.save(oldOwner);
                }
                // Agregar la mascota a la lista del nuevo propietario
                Propietario newOwner = propietarioRepository.findById(pet.getOwnerId()).orElse(null);
                if (newOwner != null) {
                    newOwner.addPet(pet);
                    propietarioRepository.save(newOwner);
                }
            }
            // Actualizar la información de la mascota
            pets.set(index, pet);
        }
    }

    public void deleteById(int id) {
        Pet pet = findById(id);
        if (pet != null) {
            // Eliminar la mascota de la lista del propietario
            Propietario propietario = propietarioRepository.findById(pet.getOwnerId()).orElse(null);
            if (propietario != null) {
                propietario.getMascotas().remove(pet);
                propietarioRepository.save(propietario);
            }
            pets.remove(pet);
        }
    }

    private int findIndexById(int id) {
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private List<Pet> generateRandomPets(int count, List<Propietario> propietarios) {
        List<Pet> pets = new ArrayList<>();
        Random random = new Random();
        String[] names = {"Max", "Bella", "Charlie", "Lucy", "Milo", "Luna"};
        String[] breeds = {"Golden Retriever", "Bulldog", "Poodle", "Beagle", "Shih Tzu"};
        String[] illnesses = {"Saludable", "Herido", "Enfermo", "En Observación"};
        String[] statuses = {"Activo", "Inactivo"};
        String[] photoUrls = {
            "https://hips.hearstapps.com/hmg-prod/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=0.752xw:1.00xh;0.175xw,0&resize=1200:*",
            "https://www.hartz.com/wp-content/uploads/2022/04/small-dog-owners-1.jpg",
            "https://static.vecteezy.com/system/resources/thumbnails/005/857/332/small_2x/funny-portrait-of-cute-corgi-dog-outdoors-free-photo.jpg"
        };

        for (int i = 1; i <= count; i++) {
            Propietario randomOwner = propietarios.get(random.nextInt(propietarios.size()));
            Pet pet = new Pet(
                    i,
                    names[random.nextInt(names.length)],
                    breeds[random.nextInt(breeds.length)],
                    random.nextInt(15), // Edad entre 0 y 14
                    random.nextInt(31), // Peso entre 0 y 30 kg
                    illnesses[random.nextInt(illnesses.length)],
                    photoUrls[random.nextInt(photoUrls.length)],
                    statuses[random.nextInt(statuses.length)],
                    randomOwner.getCedula() // Guardamos solo el ID del propietario
            );
            pets.add(pet);
            randomOwner.addPet(pet); // Agregar la mascota al propietario
        }

        return pets;
    }
}
