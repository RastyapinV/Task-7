package ru.itmentor.spring.boot_security.demo.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.models.Role;
import ru.itmentor.spring.boot_security.demo.models.User;
import ru.itmentor.spring.boot_security.demo.services.RolesService;
import ru.itmentor.spring.boot_security.demo.services.UsersService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admins")
@AllArgsConstructor
public class AdminsController {

    private final UsersService usersService;
    private final RolesService rolesService;

    @GetMapping
    public String adminTable(Model model) {
        model.addAttribute("users", usersService.index());
        return "admins/admin";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        getAllRolesList(model);
        return "admins/new";
    }

    @PostMapping
    public String create(@ModelAttribute("user") @Validated(User.Save.class) User user, BindingResult bindingResult,
                         @RequestParam("selectedRoles") List<String> selectedRoles, Model model) {
        if (bindingResult.hasErrors()) {
            getAllRolesList(model);
            return "admins/new";
        }

        Set<String> userRoles = new HashSet<>(selectedRoles);

        usersService.save(user);

        for (String roleName : userRoles) {
            Role role = new Role();
            role.setName(roleName);
            role.setUser(user);
            rolesService.saveRole(role);
        }

        return "redirect:/admins";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable int id) {
        model.addAttribute("user", usersService.getUser(id));
        return "admins/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable int id) {
        if (bindingResult.hasErrors())
            return "admins/edit";

        usersService.update(id, user);
        return "redirect:/admins";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        rolesService.deleteRole(id);
        usersService.delete(id);
        return "redirect:/admins";
    }

    private void getAllRolesList(Model model) {
        List<Role> allRoles = rolesService.getAllRoles().stream().map(Role::getName).distinct().map(rolesService::findRoleByName)
                .collect(Collectors.toList());
        model.addAttribute("allRoles", allRoles);
    }
}
