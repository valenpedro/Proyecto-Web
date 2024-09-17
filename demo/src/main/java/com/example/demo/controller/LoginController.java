package com.example.demo.controller;

import com.example.demo.model.Administrador;
import com.example.demo.model.Pet;
import com.example.demo.model.Propietario;
import com.example.demo.model.Veterinario;
import com.example.demo.service.AdministradorService;
import com.example.demo.service.PropietarioService;
import com.example.demo.service.VeterinarioService;
import jakarta.servlet.http.HttpSession;
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

    @Autowired
    private VeterinarioService veterinarioService;

    @Autowired
    private AdministradorService administradorService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("contrasena") String contrasena,
                        @RequestParam("role") String role,
                        Model model, HttpSession session) {

        switch (role) {
            case "dueno":
                Optional<Propietario> propietarioOpt = propietarioService.findAll().stream()
                    .filter(prop -> prop.getCorreo().equals(email))
                    .findFirst();

                if (propietarioOpt.isPresent()) {
                    Propietario propietario = propietarioOpt.get();
                    if (propietario.getContrasena().equals(contrasena)) {
                        session.setAttribute("usuarioLogueado", propietario);
                        return "redirect:/panel_propietario";
                    } else {
                        model.addAttribute("error", "Contraseña incorrecta");
                        return "login";
                    }
                } else {
                    model.addAttribute("error", "Correo electrónico no encontrado");
                    return "login";
                }

            case "veterinario":
                Optional<Veterinario> veterinarioOpt = veterinarioService.findAll().stream()
                    .filter(vet -> vet.getCorreo().equals(email))
                    .findFirst();

                if (veterinarioOpt.isPresent()) {
                    Veterinario veterinario = veterinarioOpt.get();
                    if (veterinario.getContrasena().equals(contrasena)) {
                        session.setAttribute("usuarioLogueado", veterinario);
                        return "redirect:/panelveterinario";
                    } else {
                        model.addAttribute("error", "Contraseña incorrecta");
                        return "login";
                    }
                } else {
                    model.addAttribute("error", "Correo electrónico no encontrado");
                    return "login";
                }

            case "administrador":
                Optional<Administrador> administradorOpt = administradorService.findAll().stream()
                    .filter(admin -> admin.getCorreo().equals(email))
                    .findFirst();

                if (administradorOpt.isPresent()) {
                    Administrador administrador = administradorOpt.get();
                    if (administrador.getContrasena().equals(contrasena)) {
                        session.setAttribute("usuarioLogueado", administrador);
                        return "redirect:/panel_administrador";
                    } else {
                        model.addAttribute("error", "Contraseña incorrecta");
                        return "login";
                    }
                } else {
                    model.addAttribute("error", "Correo electrónico no encontrado");
                    return "login";
                }

            default:
                model.addAttribute("error", "Rol no válido");
                return "login";
        }
    }

    @GetMapping("/panel_propietario")
    public String panelPropietario(Model model, HttpSession session) {
        Propietario propietario = (Propietario) session.getAttribute("usuarioLogueado");
        if (propietario == null) {
            return "redirect:/login";
        }
        model.addAttribute("primerNombre", propietario.getNombre().split(" ")[0]);
        model.addAttribute("mascotas", propietario.getMascotas());
        return "panel_propietario";
    }

    @GetMapping("/panelveterinario")
    public String panelVeterinario(Model model, HttpSession session) {
        Veterinario veterinario = (Veterinario) session.getAttribute("usuarioLogueado");
        if (veterinario == null) {
            return "redirect:/login";
        }
        // Añadimos el primer nombre del veterinario al modelo
        model.addAttribute("primerNombre", veterinario.getNombre().split(" ")[0]);
        return "panelveterinario";
    }


    @GetMapping("/panel_administrador")
    public String panelAdministrador(Model model, HttpSession session) {
        Administrador administrador = (Administrador) session.getAttribute("usuarioLogueado");
        if (administrador == null) {
            return "redirect:/login";
        }
        return "panel_administrador";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
