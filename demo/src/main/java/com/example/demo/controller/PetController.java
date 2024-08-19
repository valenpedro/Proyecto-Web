package com.example.demo.controller;

import com.example.demo.model.Pet;
import com.example.demo.model.Propietario;
import com.example.demo.service.PetService;
import com.example.demo.service.PropietarioService;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pets")
public class PetController {

    private static final Logger logger = LoggerFactory.getLogger(PetController.class);

    private final PetService petService;
    private final PropietarioService propietarioService;

    public PetController(PetService petService, PropietarioService propietarioService) {
        this.petService = petService;
        this.propietarioService = propietarioService;
        logger.info("PetController initialized");
    }

    @GetMapping
    public String viewAllPets(Model model) {
        List<Pet> pets = petService.getAllPets();
        model.addAttribute("pets", pets);
        return "pets";
    }

    @GetMapping("/{id}")
    public String viewPetDetails(@PathVariable int id, Model model) {
        Pet pet = petService.getPetById(id);
        if (pet != null) {
            model.addAttribute("pet", pet);
            Propietario propietario = propietarioService.findById(pet.getOwnerId()).orElse(null);
            model.addAttribute("propietario", propietario);
            return "pet-details";
        } else {
            return "redirect:/pets";
        }
    }

    @GetMapping("/new")
    public String showCreatePetForm(Model model) {
        model.addAttribute("pet", new Pet());
        List<Propietario> propietarios = propietarioService.findAll();
        model.addAttribute("propietarios", propietarios);
        return "pet-form";
    }

    @PostMapping
	public String savePet(@ModelAttribute Pet pet) {
		Propietario propietario = propietarioService.findById(pet.getOwnerId()).orElse(null);
		if (propietario != null) {
			propietario.addPet(pet); // Agregar la mascota a la lista del propietario
			propietarioService.save(propietario); // Guardar los cambios en el propietario
		}
		petService.savePet(pet); // Guardar la mascota en el repositorio de mascotas
		return "redirect:/pets";
	}


    @GetMapping("/edit/{id}")
    public String showEditPetForm(@PathVariable int id, Model model) {
        Pet pet = petService.getPetById(id);
        if (pet != null) {
            model.addAttribute("pet", pet);
            List<Propietario> propietarios = propietarioService.findAll();
            model.addAttribute("propietarios", propietarios);
            return "pet-form";
        } else {
            return "redirect:/pets";
        }
    }

    @PostMapping("/edit/{id}")
    public String updatePet(@PathVariable int id, @ModelAttribute Pet petDetails) {
        Pet existingPet = petService.getPetById(id);
        if (existingPet != null) {
            existingPet.setName(petDetails.getName());
            existingPet.setBreed(petDetails.getBreed());
            existingPet.setAge(petDetails.getAge());
            existingPet.setWeight(petDetails.getWeight());
            existingPet.setIllness(petDetails.getIllness());
            existingPet.setPhotoUrl(petDetails.getPhotoUrl());
            existingPet.setStatus(petDetails.getStatus());
            existingPet.setOwnerId(petDetails.getOwnerId());
            petService.savePet(existingPet);
        }
        return "redirect:/pets";
    }

    @GetMapping("/delete/{id}")
    public String deletePet(@PathVariable int id) {
        petService.deletePetById(id);
        return "redirect:/pets";
    }
}
