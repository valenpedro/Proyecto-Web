package com.example.demo.controller;

import com.example.demo.model.Propietario;
import com.example.demo.service.PropietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/propietarios")
public class PropietarioController {

    @Autowired
    private PropietarioService propietarioService;

    @GetMapping
    public String getAllPropietarios(Model model) {
        List<Propietario> propietarios = propietarioService.findAll();
        model.addAttribute("propietarios", propietarios);
        return "propietario-list";
    }

    @GetMapping("/nuevo")
    public String showCreateForm(Model model) {
        model.addAttribute("propietario", new Propietario());
        model.addAttribute("isEdit", false);
        return "propietario-form";
    }

    @PostMapping("/nuevo")
    public String createPropietario(@ModelAttribute Propietario propietario) {
        propietarioService.save(propietario);
        return "redirect:/propietarios";
    }

    @GetMapping("/editar/{cedula}")
    public String showEditForm(@PathVariable String cedula, Model model) {
        Optional<Propietario> propietario = propietarioService.findById(cedula);
        if (propietario.isPresent()) {
            model.addAttribute("propietario", propietario.get());
            model.addAttribute("isEdit", true);
            return "propietario-form";
        } else {
            return "redirect:/propietarios";
        }
    }

    @PostMapping("/editar/{cedula}")
    public String updatePropietario(@PathVariable String cedula, @ModelAttribute Propietario propietarioDetails) {
        Optional<Propietario> propietario = propietarioService.findById(cedula);
        if (propietario.isPresent()) {
            Propietario updatedPropietario = propietario.get();
            updatedPropietario.setNombre(propietarioDetails.getNombre());
            updatedPropietario.setCorreo(propietarioDetails.getCorreo());
            updatedPropietario.setCelular(propietarioDetails.getCelular());
            propietarioService.save(updatedPropietario);
        }
        return "redirect:/propietarios";
    }

    @GetMapping("/eliminar/{cedula}")
    public String deletePropietario(@PathVariable String cedula) {
        propietarioService.deleteById(cedula);
        return "redirect:/propietarios";
    }
}