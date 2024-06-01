package ru.itmentor.spring.boot_security.demo.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.models.Role;
import ru.itmentor.spring.boot_security.demo.models.User;
import ru.itmentor.spring.boot_security.demo.services.RolesService;
import ru.itmentor.spring.boot_security.demo.services.UsersService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admins/rest")
@AllArgsConstructor
public class AdminsRestController {

    private final UsersService usersService;
    private final RolesService rolesService;

    @GetMapping
    @JsonView(User.Views.Index.class)
    public List<User> index() {
        return usersService.index();
    }

    @PostMapping
    @JsonView(User.Views.Save.class)
    public String create(@RequestBody User user) {
        usersService.save(user);
        Set<Role> roles = new HashSet<>(user.getRoles());
        for (Role role : roles) {
            role.setUser(user);
            rolesService.saveRole(role);
        }
        return "User: " + user + "was saved.";
    }

    @PutMapping("/{id}")
    @JsonView(User.Views.Edit.class)
    public String update(@RequestBody User user, @PathVariable int id) {
        usersService.update(id, user);
        return "User: " + user + "was updated.";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        rolesService.deleteRole(id);
        usersService.delete(id);
        return "User with id " + id + " was deleted.";
    }

}
