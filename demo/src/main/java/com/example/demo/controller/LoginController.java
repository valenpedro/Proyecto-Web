package com.example.demo.controller;

import com.example.demo.model.Pet;
import com.example.demo.model.Propietario;
import com.example.demo.service.PropietarioService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.List;
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
						Model model, HttpSession session) {
		Optional<Propietario> propietarioOpt = propietarioService.findAll().stream()
			.filter(prop -> prop.getCorreo().equals(email))
			.findFirst();

		if (propietarioOpt.isPresent()) {
			Propietario propietario = propietarioOpt.get();
			if (propietario.getContrasena().equals(contrasena)) {
				session.setAttribute("usuarioLogueado", propietario); // Guardar propietario en la sesión
				model.addAttribute("propietario", propietario); // Pasar propietario al modelo
				return "redirect:/panel_propietario"; // Redirige al panel del propietario
			} else {
				model.addAttribute("error", "Contraseña incorrecta");
				return "login";
			}
		} else {
			model.addAttribute("error", "Correo electrónico no encontrado");
			return "login";
		}
	}

	@GetMapping("/panel_propietario")
	public String panelPropietario(Model model, HttpSession session) {
		// Obtener el propietario autenticado desde la sesión
		Propietario propietario = (Propietario) session.getAttribute("usuarioLogueado");
		
		// Si no hay propietario en la sesión, redirigir a la página de login
		if (propietario == null) {
			return "redirect:/login";
		}

		// Extraer el primer nombre del propietario
		String primerNombre = propietario.getNombre().split(" ")[0];
		model.addAttribute("primerNombre", primerNombre); // Agregar el primer nombre al modelo
		
		// Obtener la lista de mascotas del propietario autenticado
		List<Pet> mascotas = propietario.getMascotas();
		model.addAttribute("mascotas", mascotas); // Pasar la lista de mascotas al modelo
		
		// Devolver la vista del panel del propietario
		return "panel_propietario";
	}



    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalida la sesión
        return "index"; // Redirige a la página de login
    }
}
