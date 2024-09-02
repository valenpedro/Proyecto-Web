package com.example.demo.config;

import com.example.demo.model.Pet;
import com.example.demo.model.Propietario;
import com.example.demo.repository.PetRepository;
import com.example.demo.repository.PropietarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(PropietarioRepository propietarioRepository, PetRepository petRepository) {
        return args -> {
            List<Propietario> propietarios = new ArrayList<>();
            List<Pet> mascotas = new ArrayList<>();
            Set<Long> cedulasGeneradas = new HashSet<>();

            // Generar 50 propietarios con cédulas únicas de 10 dígitos
            Random random = new Random();
            for (int i = 1; i <= 50; i++) {
                long cedula;
                do {
                    cedula = 1000000000L + (long) (random.nextDouble() * 9000000000L);
                } while (cedulasGeneradas.contains(cedula)); // Asegura que la cédula sea única
                cedulasGeneradas.add(cedula);

                Propietario propietario = new Propietario(
                        String.valueOf(cedula), // Convertir a String
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
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSd6gX-aI8YrWzwHJ3mH4zh183xK6qQ4GBmEA&s",
                    "https://preview.redd.it/4q6g95op4txa1.jpg?width=640&crop=smart&auto=webp&s=cdcb1d017537b558648f34dba0920bd2642028e6",
                    "https://w7.pngwing.com/pngs/947/885/png-transparent-clifford-the-big-red-dog-the-little-red-hen-puppy-dog-breed-dog-thumbnail.png",
                    "https://i.pinimg.com/736x/59/46/ae/5946ae711b61a1937c65a892dccfc6f5.jpg"
            };

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
