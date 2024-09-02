package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping("/templates/login.html")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    @GetMapping("/login.html")
    public String redirectLogin() {
        return "redirect:/login";
    }

    @GetMapping("/pets/login.html")
    public String redirectPetsLogin() {
        return "redirect:/login";
    }

    @GetMapping("/propietarios/")
    public String redirectPropietarios() {
        return "redirect:/propietarios";
    }

    @GetMapping("/pets/")
    public String redirectPets() {
        return "redirect:/pets";
    }
}
