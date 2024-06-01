package ru.itmentor.spring.boot_security.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmentor.spring.boot_security.demo.models.Role;

public interface RolesDAO extends JpaRepository<Role, Long> {

     Role findFirstByName(String role);
     void deleteAllByUserId(Long id);

}
