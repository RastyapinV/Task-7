package ru.itmentor.spring.boot_security.demo.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmentor.spring.boot_security.demo.models.User;
import ru.itmentor.spring.boot_security.demo.services.UsersService;

import java.security.Principal;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        String username = principal.getName();
        User user = usersService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "users/profile";
    }

}
