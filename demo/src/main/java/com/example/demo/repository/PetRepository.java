package com.example.demo.repository;

import com.example.demo.model.Pet;
import com.example.demo.model.Propietario;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PetRepository {
    private Map<Integer, Pet> petMap = new HashMap<>();  // Usar un HashMap para almacenar las mascotas

    private final PropietarioRepository propietarioRepository;

    public PetRepository(PropietarioRepository propietarioRepository) {
        this.propietarioRepository = propietarioRepository;

        List<Propietario> propietarios = propietarioRepository.findAll();
        if (propietarios.isEmpty()) {
            throw new IllegalArgumentException("La lista de propietarios no puede estar vacía");
        }

        // Generar mascotas aleatorias y almacenarlas en el HashMap
        generateRandomPets(50, propietarios);
    }

    public List<Pet> findAll() {
        return new ArrayList<>(petMap.values());  // Convertir el HashMap en una lista
    }

    public Pet findById(int id) {
        return petMap.get(id);  // Obtener la mascota por su ID
    }

    public void save(Pet pet) {
        if (pet.getId() == 0) {
            // Nueva mascota - Genera un nuevo ID solo si es un nuevo pet (ID es 0)
            pet.setId(generateNewId());
        }
    
        // Guardar o actualizar la mascota en el HashMap
        petMap.put(pet.getId(), pet);
        
        // Manejo de propietarios (verificar si ha cambiado el propietario, etc.)
        Propietario propietario = propietarioRepository.findById(pet.getOwnerId()).orElse(null);
        if (propietario != null) {
            // Verificar si la mascota pertenece a un nuevo propietario
            if (!propietario.getMascotas().contains(pet)) {
                // Quitar la mascota de la lista del propietario anterior si ha cambiado
                petMap.values().stream()
                    .filter(existingPet -> existingPet.getId() == pet.getId())
                    .findFirst()
                    .ifPresent(existingPet -> {
                        Propietario oldOwner = propietarioRepository.findById(existingPet.getOwnerId()).orElse(null);
                        if (oldOwner != null && !oldOwner.getCedula().equals(propietario.getCedula())) {
                            oldOwner.getMascotas().remove(existingPet);
                            propietarioRepository.save(oldOwner);
                        }
                    });
    
                // Agregar la mascota a la lista del nuevo propietario
                propietario.addPet(pet);
                propietarioRepository.save(propietario);
            }
        }
    }    

    public void deleteById(int id) {
        Pet pet = findById(id);
        if (pet != null) {
            // Eliminar la mascota del HashMap
            petMap.remove(id);

            // Eliminar la mascota de la lista del propietario
            Propietario propietario = propietarioRepository.findById(pet.getOwnerId()).orElse(null);
            if (propietario != null) {
                propietario.getMascotas().remove(pet);
                propietarioRepository.save(propietario);
            }
        }
    }

    private int generateNewId() {
        if (petMap.isEmpty()) {
            return 1;  // Si el mapa está vacío, el primer ID será 1
        } else {
            return Collections.max(petMap.keySet()) + 1;  // Generar un nuevo ID basado en el mayor ID existente
        }
    }
    

    private void generateRandomPets(int count, List<Propietario> propietarios) {
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
                generateNewId(),
                names[random.nextInt(names.length)],
                breeds[random.nextInt(breeds.length)],
                random.nextInt(15), // Edad entre 0 y 14
                random.nextInt(31), // Peso entre 0 y 30 kg
                illnesses[random.nextInt(illnesses.length)],
                photoUrls[random.nextInt(photoUrls.length)],
                statuses[random.nextInt(statuses.length)],
                randomOwner.getCedula() // Guardamos solo el ID del propietario
            );
            petMap.put(pet.getId(), pet);  // Agregar la mascota al HashMap
            randomOwner.addPet(pet); // Agregar la mascota al propietario
        }
    }
}
