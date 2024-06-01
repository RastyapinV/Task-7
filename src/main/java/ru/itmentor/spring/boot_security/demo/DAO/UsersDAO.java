package ru.itmentor.spring.boot_security.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmentor.spring.boot_security.demo.models.User;

import java.util.Optional;

public interface UsersDAO extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    default void update(Long id, User updatedUser) {
        updatedUser.setId(id);
        save(updatedUser);
    }

}
