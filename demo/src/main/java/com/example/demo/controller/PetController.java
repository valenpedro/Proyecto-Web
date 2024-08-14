package com.example.demo.controller;

import com.example.demo.model.Pet;
import com.example.demo.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pets") // Esta es la ruta base para todas las rutas de mascotas
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public String viewAllPets(Model model) {
        List<Pet> pets = petService.getAllPets();
        model.addAttribute("pets", pets);
        return "pets"; // Asegúrate de que "pets.html" esté en /templates/
    }

    @GetMapping("/{id}")
    public String viewPetDetails(@PathVariable int id, Model model) {
        Pet pet = petService.getPetById(id);
        if (pet != null) {
            model.addAttribute("pet", pet);
            return "pet-details"; // Asegúrate de que "pet-details.html" esté en /templates/
        } else {
            return "redirect:/pets"; // Redirige a la lista si la mascota no se encuentra
        }
    }
}
