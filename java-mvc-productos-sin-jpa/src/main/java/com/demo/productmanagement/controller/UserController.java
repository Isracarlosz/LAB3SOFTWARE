package com.demo.productmanagement.controller;

import com.demo.productmanagement.model.User;
import com.demo.productmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/list";
    }

    @GetMapping("/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        return "users/form";
    }

    @PostMapping
    public String saveUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("message", "Usuario guardado con éxito");
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<User> optionalUser = userService.getUserById(id);

        if (optionalUser.isPresent()) {
            model.addAttribute("user", optionalUser.get());
            return "users/form";
        } else {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/users";
        }
    }

    @GetMapping("/view/{id}")
    public String viewUser(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<User> optionalUser = userService.getUserById(id);

        if (optionalUser.isPresent()) {
            model.addAttribute("user", optionalUser.get());
            return "users/view";
        } else {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/users";
        }
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        // La lógica para no sobrescribir la contraseña debe estar en UserService.updateUser
        boolean updated = userService.updateUser(user);

        if (updated) {
            redirectAttributes.addFlashAttribute("message", "Usuario actualizado con éxito");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el usuario");
        }

        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean deleted = userService.deleteUser(id);

        if (deleted) {
            redirectAttributes.addFlashAttribute("message", "Usuario eliminado con éxito");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el usuario");
        }

        return "redirect:/users";
    }

}
