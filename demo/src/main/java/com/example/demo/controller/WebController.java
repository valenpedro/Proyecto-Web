package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Se refiere al archivo login.html en /src/main/resources/templates/
    }

    @GetMapping("/")
    public String index() {
        return "index"; // Se refiere al archivo index.html en /src/main/resources/templates/
    }
}
