package ru.itmentor.spring.boot_security.demo.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmentor.spring.boot_security.demo.models.User;
import ru.itmentor.spring.boot_security.demo.services.UsersService;

import java.security.Principal;

@RestController
@RequestMapping("/users/rest")
@AllArgsConstructor
public class UsersRestController {

    private final UsersService usersService;

    @GetMapping
    @JsonView(User.Views.Index.class)
    public User profile(Principal principal) {
        String username = principal.getName();
        return usersService.getUserByUsername(username);
    }

}
