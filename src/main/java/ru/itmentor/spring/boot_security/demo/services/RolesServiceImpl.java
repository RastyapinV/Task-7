package ru.itmentor.spring.boot_security.demo.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.DAO.RolesDAO;
import ru.itmentor.spring.boot_security.demo.models.Role;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class RolesServiceImpl implements RolesService{

    private final RolesDAO rolesDAO;

    @Override
    public void saveRole(Role role) {
        rolesDAO.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return rolesDAO.findAll();
    }

    @Override
    public void deleteRole(long id) {
        rolesDAO.deleteAllByUserId(id);
    }

    @Override
    public Role findRoleByName(String role) {
        return rolesDAO.findFirstByName(role);
    }
}
