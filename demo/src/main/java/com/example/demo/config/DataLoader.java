package com.example.demo.config;

import com.example.demo.model.Pet;
import com.example.demo.model.Propietario;
import com.example.demo.repository.PetRepository;
import com.example.demo.repository.PropietarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(PropietarioRepository propietarioRepository, PetRepository petRepository) {
        return args -> {
            List<Propietario> propietarios = new ArrayList<>();
            List<Pet> mascotas = new ArrayList<>();

            // Generar 50 propietarios
            for (int i = 1; i <= 50; i++) {
                Propietario propietario = new Propietario(
                        "C" + i, // Cédula única por propietario
                        "Propietario " + i,
                        "email" + i + "@example.com",
                        "123456789" + i,
                        "password" + i
                );
                propietarios.add(propietario);
            }
            propietarioRepository.saveAll(propietarios);

            // Generar 100 mascotas, 2 por propietario
            String[] nombres = {"Max", "Bella", "Charlie", "Lucy", "Milo", "Luna", "Rocky", "Coco", "Buddy", "Daisy"};
            String[] razas = {"Golden Retriever", "Bulldog", "Poodle", "Beagle", "Shih Tzu", "Chihuahua", "Pomeranian"};
            String[] enfermedades = {"Saludable", "Herido", "Enfermo", "En Observación"};
            String[] estados = {"Activo", "Inactivo"};
            String[] urlsFotos = {
                    "https://example.com/photo1.jpg",
                    "https://example.com/photo2.jpg",
                    "https://example.com/photo3.jpg"
            };

            Random random = new Random();

            for (Propietario propietario : propietarios) {
                for (int j = 0; j < 2; j++) { // 2 mascotas por propietario
                    Pet pet = new Pet(
                            nombres[random.nextInt(nombres.length)],
                            razas[random.nextInt(razas.length)],
                            random.nextInt(15), // Edad entre 0 y 14
                            random.nextFloat() * 30, // Peso entre 0 y 30 kg
                            enfermedades[random.nextInt(enfermedades.length)],
                            urlsFotos[random.nextInt(urlsFotos.length)],
                            estados[random.nextInt(estados.length)],
                            propietario
                    );
                    mascotas.add(pet);
                }
            }
            petRepository.saveAll(mascotas);
        };
    }
}
