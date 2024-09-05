package com.example.demo.controller;

import com.example.demo.model.Pet;
import com.example.demo.model.Propietario;
import com.example.demo.model.Veterinario;
import com.example.demo.service.PetService;
import com.example.demo.service.PropietarioService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Controller
@RequestMapping("/pets")
public class PetController {

    private static final Logger logger = LoggerFactory.getLogger(PetController.class);
    private final PetService petService;
    private final PropietarioService propietarioService;

    @Autowired
    public PetController(PetService petService, PropietarioService propietarioService) {
        this.petService = petService;
        this.propietarioService = propietarioService;
        logger.info("PetController initialized");
    }

    // Verifica si el usuario es un veterinario
    private boolean isVeterinario(HttpSession session) {
        return session.getAttribute("usuarioLogueado") instanceof Veterinario;
    }

    // Verifica si el propietario logueado es el dueño de la mascota
    private boolean esPropietarioDeLaMascota(Propietario propietarioLogueado, Pet pet) {
        return propietarioLogueado != null && pet.getPropietario().getCedula().equals(propietarioLogueado.getCedula());
    }

    @GetMapping
    public String viewAllPets(Model model, HttpSession session) {
        if (!isVeterinario(session)) {
            return "redirect:/login";
        }
        logger.info("Request to view all pets");
        Veterinario veterinario = (Veterinario) session.getAttribute("usuarioLogueado");
        model.addAttribute("primerNombre", veterinario.getNombre().split(" ")[0]);

        List<Pet> pets = petService.getAllPets();
        model.addAttribute("pets", pets);
        return "pets";
    }

    @GetMapping("/{id}")
    public String viewPetDetails(@PathVariable int id, Model model, HttpSession session) {
        Optional<Pet> petOpt = petService.getPetById(id);
        if (petOpt.isPresent()) {
            Pet pet = petOpt.get();
            Object usuarioLogueado = session.getAttribute("usuarioLogueado");

            // Verifica si el usuario logueado es un veterinario
            if (usuarioLogueado instanceof Veterinario) {
                Veterinario veterinario = (Veterinario) usuarioLogueado;
                model.addAttribute("primerNombre", veterinario.getNombre().split(" ")[0]);
                model.addAttribute("pet", pet);
                model.addAttribute("propietario", pet.getPropietario());
                return "pet-details";

            // Verifica si el usuario logueado es un propietario
            } else if (usuarioLogueado instanceof Propietario) {
                Propietario propietario = (Propietario) usuarioLogueado;

                // Verifica que el propietario sea el dueño de la mascota
                if (esPropietarioDeLaMascota(propietario, pet)) {
                    model.addAttribute("primerNombre", propietario.getNombre().split(" ")[0]);
                    model.addAttribute("pet", pet);
                    model.addAttribute("propietario", pet.getPropietario());
                    return "pet-details";
                } else {
                    return "redirect:/login"; // Redirige si no es el dueño de la mascota
                }
            } else {
                return "redirect:/login"; // Redirige si el usuario logueado no es ni veterinario ni propietario
            }
        } else {
            return "redirect:/pets"; // Redirige si la mascota no se encuentra
        }
    }


    @GetMapping("/new")
    public String showCreatePetForm(Model model, HttpSession session) {
        if (!isVeterinario(session)) {
            return "redirect:/login";
        }
        Veterinario veterinario = (Veterinario) session.getAttribute("usuarioLogueado");
        model.addAttribute("primerNombre", veterinario.getNombre().split(" ")[0]);

        model.addAttribute("pet", new Pet());
        List<Propietario> propietarios = propietarioService.findAll();
        model.addAttribute("propietarios", propietarios);
        model.addAttribute("isEdit", false);
        return "pet-form";
    }

    @PostMapping("/new")
    public String saveNewPet(@ModelAttribute Pet pet, Model model, HttpSession session) {
        if (!isVeterinario(session)) {
            return "redirect:/login";
        }
        logger.info("Request to save new pet: {}", pet);

        Optional<Propietario> propietario = propietarioService.findByCedula(pet.getPropietario().getCedula());

        if (propietario.isPresent()) {
            pet.setPropietario(propietario.get());
            petService.savePet(pet);
            logger.info("Pet saved successfully: {}", pet);
            return "redirect:/pets";
        } else {
            logger.warn("Propietario with cedula: {} not found, cannot save pet", pet.getPropietario().getCedula());
            model.addAttribute("error", "Propietario no encontrado.");
            return "pet-form";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditPetForm(@PathVariable int id, Model model, HttpSession session) {
        if (!isVeterinario(session)) {
            return "redirect:/login";
        }
        Veterinario veterinario = (Veterinario) session.getAttribute("usuarioLogueado");
        model.addAttribute("primerNombre", veterinario.getNombre().split(" ")[0]);

        Optional<Pet> pet = petService.getPetById(id);
        if (pet.isPresent()) {
            model.addAttribute("pet", pet.get());
            List<Propietario> propietarios = propietarioService.findAll();
            model.addAttribute("propietarios", propietarios);
            model.addAttribute("isEdit", true);
            return "pet-form";
        } else {
            return "redirect:/pets";
        }
    }

    @PostMapping("/edit/{id}")
    public String updatePet(@PathVariable int id, @ModelAttribute Pet petDetails, Model model, HttpSession session) {
        if (!isVeterinario(session)) {
            return "redirect:/login";
        }
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

            Optional<Propietario> propietario = propietarioService.findByCedula(petDetails.getPropietario().getCedula());
            if (propietario.isPresent()) {
                pet.setPropietario(propietario.get());
            } else {
                logger.warn("Propietario with cedula: {} not found, cannot update pet", petDetails.getPropietario().getCedula());
                model.addAttribute("error", "Propietario no encontrado.");
                return "pet-form";
            }

            petService.savePet(pet);
            logger.info("Pet updated successfully: {}", pet);
            return "redirect:/pets";
        } else {
            logger.warn("Pet with id: {} not found, cannot update", id);
            return "redirect:/pets";
        }
    }

    @GetMapping("/delete/{id}")
    public String deletePet(@PathVariable int id, HttpSession session) {
        if (!isVeterinario(session)) {
            return "redirect:/login";
        }
        logger.info("Request to delete pet with id: {}", id);
        petService.deletePetById(id);
        return "redirect:/pets";
    }
}
