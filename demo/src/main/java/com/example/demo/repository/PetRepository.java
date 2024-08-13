package com.example.demo.repository;

import com.example.demo.model.Pet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class PetRepository {
    private List<Pet> pets;

    public PetRepository() {
        this.pets = generateRandomPets(50); // Change 20 to any number of pets you want to create
    }

    public List<Pet> findAll() {
        return pets;
    }

    public Pet findById(int id) {
        return pets.stream().filter(pet -> pet.getId() == id).findFirst().orElse(null);
    }

    private List<Pet> generateRandomPets(int count) {
        List<Pet> pets = new ArrayList<>();
        Random random = new Random();
        String[] names = {"Max", "Bella", "Charlie", "Lucy", "Milo", "Luna"};
        String[] breeds = {"Golden Retriever", "Bulldog", "Poodle", "Beagle", "Shih Tzu"};
        String[] illnesses = {"Healthy", "Injured", "Sick", "Under Observation"};
        String[] statuses = {"Active", "Inactive"};
        String[] photoUrls = {
                "http://example.com/max.jpg",
                "http://example.com/bella.jpg",
                "http://example.com/charlie.jpg",
                "http://example.com/lucy.jpg",
                "http://example.com/milo.jpg",
                "http://example.com/luna.jpg"
        };

        for (int i = 1; i <= count; i++) {
            pets.add(new Pet(
                    i,
                    names[random.nextInt(names.length)],
                    breeds[random.nextInt(breeds.length)],
                    random.nextInt(15), // Age between 0 and 14
                    random.nextFloat() * 30, // Weight between 0 and 30 kg
                    illnesses[random.nextInt(illnesses.length)],
                    photoUrls[random.nextInt(photoUrls.length)],
                    statuses[random.nextInt(statuses.length)]
            ));
        }

        return pets;
    }
}
