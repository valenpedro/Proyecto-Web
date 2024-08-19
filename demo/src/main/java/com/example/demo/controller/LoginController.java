package com.example.demo.controller;

import com.example.demo.model.Propietario;
import com.example.demo.service.PropietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private PropietarioService propietarioService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email, 
                        @RequestParam("contrasena") String contrasena, 
                        Model model) {
        Optional<Propietario> propietarioOpt = propietarioService.findAll().stream()
            .filter(prop -> prop.getCorreo().equals(email))
            .findFirst();
    
        if (propietarioOpt.isPresent()) {
            Propietario propietario = propietarioOpt.get();
            if (propietario.getContrasena().equals(contrasena)) {
                return "redirect:/panel_propietario"; // Redirige al panel del propietario
            } else {
                model.addAttribute("error", "Contraseña incorrecta");
                return "login"; // Redirige de nuevo al login con el mensaje de error
            }
        } else {
            model.addAttribute("error", "Correo electrónico no encontrado");
            return "login"; // Redirige de nuevo al login con el mensaje de error
        }
    }

    @GetMapping("/panel_propietario")
    public String panelPropietario() {
        return "panel_propietario";
    }
}