package com.example.demo.controller;

import com.example.demo.model.Pet;
import com.example.demo.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pets") // Esta es la ruta base para todas las rutas de mascotas
public class PetController {

    private final PetService petService;

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

    @GetMapping("/new")
    public String showCreatePetForm(Model model) {
        model.addAttribute("pet", new Pet()); // Añade un objeto Pet vacío al modelo
        return "pet-form";  // Muestra el formulario de creación (pet-form.html)
    }

    @PostMapping
    public String savePet(@ModelAttribute Pet pet) {
        petService.savePet(pet);
        return "redirect:/pets";  // Redirige a la lista después de guardar
    }

    @GetMapping("/edit/{id}")
    public String showEditPetForm(@PathVariable int id, Model model) {
        Pet pet = petService.getPetById(id);
        if (pet != null) {
            model.addAttribute("pet", pet);
            return "pet-form";  // Debe coincidir con el nombre del archivo en /templates/
        } else {
            return "redirect:/pets";
        }   
    }

    @GetMapping("/delete/{id}")
    public String deletePet(@PathVariable int id) {
        petService.deletePetById(id);
        return "redirect:/pets";
    }
}
