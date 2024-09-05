package com.example.demo.controller;

import com.example.demo.model.Propietario;
import com.example.demo.model.Veterinario;
import com.example.demo.service.PropietarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/propietarios")
public class PropietarioController {

    private final PropietarioService propietarioService;

    @Autowired
    public PropietarioController(PropietarioService propietarioService) {
        this.propietarioService = propietarioService;
    }

    private boolean isVeterinario(HttpSession session) {
        return session.getAttribute("usuarioLogueado") instanceof Veterinario;
    }

    @GetMapping
    public String getAllPropietarios(Model model, HttpSession session) {
        if (!isVeterinario(session)) {
            return "redirect:/login";
        }
        Veterinario veterinario = (Veterinario) session.getAttribute("usuarioLogueado");
        model.addAttribute("primerNombre", veterinario.getNombre().split(" ")[0]); // Agrega el primer nombre

        List<Propietario> propietarios = propietarioService.findAll();
        model.addAttribute("propietarios", propietarios);
        return "propietario-list";
    }


    @GetMapping("/nuevo")
    public String showCreateForm(Model model, HttpSession session) {
        if (!isVeterinario(session)) {
            return "redirect:/login";
        }
        Veterinario veterinario = (Veterinario) session.getAttribute("usuarioLogueado");
        model.addAttribute("primerNombre", veterinario.getNombre().split(" ")[0]); // Agrega el primer nombre

        model.addAttribute("propietario", new Propietario());
        model.addAttribute("isEdit", false);
        return "propietario-form";
    }


    @PostMapping("/nuevo")
    public String createPropietario(@ModelAttribute Propietario propietario, HttpSession session) {
        if (!isVeterinario(session)) {
            return "redirect:/login";
        }
        
        propietarioService.save(propietario);
        return "redirect:/propietarios";
    }

    @GetMapping("/editar/{cedula}")
    public String showEditForm(@PathVariable String cedula, Model model, HttpSession session) {
        if (!isVeterinario(session)) {
            return "redirect:/login";
        }
        Veterinario veterinario = (Veterinario) session.getAttribute("usuarioLogueado");
        model.addAttribute("primerNombre", veterinario.getNombre().split(" ")[0]); // Agrega el primer nombre

        Optional<Propietario> propietario = propietarioService.findByCedula(cedula);
        if (propietario.isPresent()) {
            model.addAttribute("propietario", propietario.get());
            model.addAttribute("isEdit", true);
            return "propietario-form";
        } else {
            return "redirect:/propietarios";
        }
    }


    @PostMapping("/editar/{cedula}")
    public String updatePropietario(@PathVariable String cedula, @ModelAttribute Propietario propietarioDetails, HttpSession session) {
        if (!isVeterinario(session)) {
            return "redirect:/login";
        }
        Optional<Propietario> propietario = propietarioService.findByCedula(cedula);
        if (propietario.isPresent()) {
            Propietario updatedPropietario = propietario.get();
            updatedPropietario.setNombre(propietarioDetails.getNombre());
            updatedPropietario.setCorreo(propietarioDetails.getCorreo());
            updatedPropietario.setCelular(propietarioDetails.getCelular());
            updatedPropietario.setContrasena(propietarioDetails.getContrasena());
            propietarioService.save(updatedPropietario);
        }
        return "redirect:/propietarios";
    }


    @GetMapping("/eliminar/{cedula}")
    public String deletePropietario(@PathVariable String cedula, HttpSession session) {
        if (!isVeterinario(session)) {
            return "redirect:/login";
        }
        propietarioService.deleteByCedula(cedula);
        return "redirect:/propietarios";
    }
}
