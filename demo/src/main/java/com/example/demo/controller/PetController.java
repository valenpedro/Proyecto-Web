package com.example.demo.controller;

import com.example.demo.model.Pet;
import com.example.demo.model.Propietario;
import com.example.demo.service.PetService;
import com.example.demo.service.PropietarioService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        logger.info("Request to view all pets");
        List<Pet> pets = petService.getAllPets();
        model.addAttribute("pets", pets);
        return "pets";
    }

    @GetMapping("/{id}")
    public String viewPetDetails(@PathVariable int id, Model model) {
        logger.info("Request to view details of pet with id: {}", id);
        Optional<Pet> pet = petService.getPetById(id);
        if (pet.isPresent()) {
            model.addAttribute("pet", pet.get());
            Propietario propietario = pet.get().getPropietario();
            model.addAttribute("propietario", propietario);
            logger.info("Displaying details for pet: {}", pet.get());
            return "pet-details";
        } else {
            logger.warn("Pet with id: {} not found", id);
            return "redirect:/pets";
        }
    }

    @GetMapping("/new")
    public String showCreatePetForm(Model model) {
        logger.info("Request to show form to create a new pet");
        model.addAttribute("pet", new Pet());
        List<Propietario> propietarios = propietarioService.findAll();
        model.addAttribute("propietarios", propietarios);
        return "pet-form";
    }

    @PostMapping
    public String savePet(@ModelAttribute Pet pet) {
        logger.info("Request to save new pet: {}", pet);
        Optional<Propietario> propietario = propietarioService.findById(pet.getPropietario().getCedula());
        if (propietario.isPresent()) {
            pet.setPropietario(propietario.get());
            petService.savePet(pet);
            logger.info("Pet saved successfully: {}", pet);
            return "redirect:/pets";
        } else {
            logger.warn("Propietario with id: {} not found, cannot save pet", pet.getPropietario().getCedula());
            return "redirect:/pets/new";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditPetForm(@PathVariable int id, Model model) {
        logger.info("Request to show form to edit pet with id: {}", id);
        Optional<Pet> pet = petService.getPetById(id);
        if (pet.isPresent()) {
            model.addAttribute("pet", pet.get());
            List<Propietario> propietarios = propietarioService.findAll();
            model.addAttribute("propietarios", propietarios);
            logger.info("Displaying edit form for pet: {}", pet.get());
            return "pet-form";
        } else {
            logger.warn("Pet with id: {} not found, cannot edit", id);
            return "redirect:/pets";
        }
    }

    @PostMapping("/edit/{id}")
    public String updatePet(@PathVariable int id, @ModelAttribute Pet petDetails) {
        logger.info("Request to update pet with id: {}", id);
        Optional<Pet> existingPet = petService.getPetById(id);
        if (existingPet.isPresent()) {
            Pet pet = existingPet.get();
            pet.setName(petDetails.getName());
            pet.setBreed(petDetails.getBreed());
            pet.setAge(petDetails.getAge());
            pet.setWeight(petDetails.getWeight());
            pet.setIllness(petDetails.getIllness());
            pet.setPhotoUrl(petDetails.getPhotoUrl());
            pet.setStatus(petDetails.getStatus());
            pet.setPropietario(petDetails.getPropietario());
            petService.savePet(pet);
            logger.info("Pet updated successfully: {}", pet);
        } else {
            logger.warn("Pet with id: {} not found, cannot update", id);
        }
        return "redirect:/pets";
    }

    @GetMapping("/delete/{id}")
	public String deletePet(@PathVariable int id, Model model) {
		logger.info("Request to delete pet with id: {}", id);
		try {
			petService.deletePetById(id);
			logger.info("Pet with id: {} deleted successfully", id);
		} catch (Exception e) {
			logger.error("Failed to delete pet with id: {}", id, e);
		}

		// Recargar la lista de mascotas después de la eliminación
		List<Pet> pets = petService.getAllPets();
		model.addAttribute("pets", pets);

		return "redirect:/pets";
	}

}
