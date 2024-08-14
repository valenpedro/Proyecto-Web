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
        this.pets = generateRandomPets(50); // Change 50 to any number of pets you want to create
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
                "https://hips.hearstapps.com/hmg-prod/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=0.752xw:1.00xh;0.175xw,0&resize=1200:*",
                "https://www.hartz.com/wp-content/uploads/2022/04/small-dog-owners-1.jpg",
                "https://static.vecteezy.com/system/resources/thumbnails/005/857/332/small_2x/funny-portrait-of-cute-corgi-dog-outdoors-free-photo.jpg"
        };

        for (int i = 1; i <= count; i++) {
            pets.add(new Pet(
                    i,
                    names[random.nextInt(names.length)],
                    breeds[random.nextInt(breeds.length)],
                    random.nextInt(15), // Age between 0 and 14
                    random.nextFloat() * 30, // Weight between 0 and 30 kg
                    illnesses[random.nextInt(illnesses.length)],
                    photoUrls[random.nextInt(photoUrls.length)], // Randomly select one of the provided URLs
                    statuses[random.nextInt(statuses.length)]
            ));
        }

        return pets;
    }
}
