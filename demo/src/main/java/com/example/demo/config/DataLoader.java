package com.example.demo.config;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(PropietarioRepository propietarioRepository, PetRepository petRepository,
                               VeterinarioRepository veterinarioRepository, MedicamentoRepository medicamentoRepository,
                               TratamientoRepository tratamientoRepository, AdministradorRepository administradorRepository) {
        return args -> {
            List<Propietario> propietarios = new ArrayList<>();
            List<Pet> mascotas = new ArrayList<>();
            List<Veterinario> veterinarios = new ArrayList<>();
            List<Medicamento> medicamentos = new ArrayList<>();
            List<Tratamiento> tratamientos = new ArrayList<>();
            Set<Long> cedulasGeneradas = new HashSet<>();

            // Generar 50 propietarios con cédulas únicas de 10 dígitos
            Random random = new Random();
            for (int i = 1; i <= 50; i++) {
                long cedula;
                do {
                    cedula = 1000000000L + (long) (random.nextDouble() * 9000000000L);
                } while (cedulasGeneradas.contains(cedula));
                cedulasGeneradas.add(cedula);

                Propietario propietario = new Propietario(
                        String.valueOf(cedula),
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
                for (int j = 0; j < 2; j++) {
                    Pet pet = new Pet(
                            nombres[random.nextInt(nombres.length)],
                            razas[random.nextInt(razas.length)],
                            random.nextInt(15),
                            random.nextFloat() * 30,
                            enfermedades[random.nextInt(enfermedades.length)],
                            urlsFotos[random.nextInt(urlsFotos.length)],
                            estados[random.nextInt(estados.length)],
                            propietario
                    );
                    mascotas.add(pet);
                }
            }
            petRepository.saveAll(mascotas);

            // Generar 10 veterinarios
            for (int i = 1; i <= 10; i++) {
                long cedula;
                do {
                    cedula = 1000000000L + (long) (random.nextDouble() * 9000000000L);
                } while (cedulasGeneradas.contains(cedula));
                cedulasGeneradas.add(cedula);

                Veterinario veterinario = new Veterinario(
                        String.valueOf(cedula),
                        "Veterinario " + i,
                        "Especialidad " + i,
                        random.nextInt(100),
                        "password" + i,
                        "veterinario" + i + "@example.com"
                );
                veterinarios.add(veterinario);
            }
            veterinarioRepository.saveAll(veterinarios);

            // Generar 20 medicamentos
            String[] nombresMedicamentos = {"Medicamento A", "Medicamento B", "Medicamento C", "Medicamento D"};
            for (int i = 1; i <= 20; i++) {
                Medicamento medicamento = new Medicamento(
                        nombresMedicamentos[random.nextInt(nombresMedicamentos.length)],
                        random.nextFloat() * 50,
                        random.nextFloat() * 100,
                        random.nextInt(100),
                        random.nextInt(50)
                );
                medicamentos.add(medicamento);
            }
            medicamentoRepository.saveAll(medicamentos);

            // Generar 30 tratamientos
            for (int i = 1; i <= 30; i++) {
                Tratamiento tratamiento = new Tratamiento(
                        new Date(),
                        mascotas.get(random.nextInt(mascotas.size())),
                        veterinarios.get(random.nextInt(veterinarios.size())),
                        medicamentos.get(random.nextInt(medicamentos.size()))
                );
                tratamientos.add(tratamiento);
            }
            tratamientoRepository.saveAll(tratamientos);

            // Generar 3 administradores
            for (int i = 1; i <= 3; i++) {
                Administrador administrador = new Administrador(
                        "admin" + i,
                        "Administrador " + i,
                        "admin" + i + "@example.com",
                        "password" + i
                );
                administradorRepository.save(administrador);
            }
        };
    }
}
