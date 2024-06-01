package ru.itmentor.spring.boot_security.demo.services;

import ru.itmentor.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RolesService {

    void saveRole(Role role);
    List<Role> getAllRoles();
    void deleteRole(long id);
    Role findRoleByName(String role);

}
