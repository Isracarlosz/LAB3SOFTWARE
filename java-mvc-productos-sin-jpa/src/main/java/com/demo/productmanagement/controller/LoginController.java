package com.demo.productmanagement.controller;

import com.demo.productmanagement.model.User;
import com.demo.productmanagement.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    // Página inicial: redirige al login
    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/login";
    }

    // Mostrar formulario de login
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    // Procesar login
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        boolean valid = userService.validateLogin(email, password);

        if (valid) {
            // Guardar el email del usuario logueado en sesión
            session.setAttribute("loggedUser", email);
            return "redirect:/users"; // Panel principal (lista de usuarios)
        } else {
            redirectAttributes.addFlashAttribute("error", "Correo o contraseña incorrectos");
            return "redirect:/login";
        }
    }

    // Cerrar sesión
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("message", "Sesión cerrada correctamente");
        return "redirect:/login";
    }
}